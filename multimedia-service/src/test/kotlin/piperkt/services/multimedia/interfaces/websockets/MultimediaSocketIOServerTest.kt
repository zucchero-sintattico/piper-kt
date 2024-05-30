package piperkt.services.multimedia.interfaces.websockets

import base.IntegrationTest
import data.UsersData.jane
import data.UsersData.john
import io.kotest.assertions.fail
import io.socket.client.IO
import io.socket.client.Socket
import jakarta.inject.Inject
import mocks.publishers.MockedSessionEventPublisher
import mocks.repositories.InMemoryDirectRepository
import mocks.repositories.InMemoryServerRepository
import mocks.repositories.InMemorySessionRepository
import piperkt.services.multimedia.application.session.SessionService
import piperkt.services.multimedia.domain.session.SessionFactory
import piperkt.services.multimedia.interfaces.websockets.MultimediaProtocolEvent.*
import piperkt.services.multimedia.interfaces.websockets.MultimediaProtocolMessage.*

class MultimediaSocketIOServerTest(@Inject private val objectMapper: JsonMapper) :
    IntegrationTest.FunSpec({
        // Setup
        val sessionRepository = InMemorySessionRepository()
        val serverRepository = InMemoryServerRepository()
        val directRepository = InMemoryDirectRepository()
        val sessionService =
            SessionService(
                sessionRepository,
                serverRepository,
                directRepository,
                MockedSessionEventPublisher()
            )
        val server = MultimediaSocketIOServer(sessionService, objectMapper)

        // Setup data
        val johnId = john().id
        val janeId = jane().id
        val session = SessionFactory.fromAllowedUsers(setOf(johnId, janeId))
        sessionRepository.save(session)

        // Start server
        server.start()

        fun authorizedClientOf(userId: String): Socket {
            val auth =
                IO.Options.builder().setExtraHeaders(mapOf("authToken" to listOf(userId))).build()
            val client = IO.socket("ws://localhost:${server.configuration.port}", auth)
            client.connect()
            return client!!
        }

        val johnClient = authorizedClientOf(johnId.value)
        val janeClient = authorizedClientOf(janeId.value)

        while (!johnClient.connected() || !janeClient.connected()) {
            println("Waiting for clients to connect")
            Thread.sleep(100)
        }

        fun Any.toJson(): String = objectMapper.toJson(this)

        test("should allow client to join") {
            val message = JoinMessage(session.id.value)
            johnClient.emit(JOIN.event, message.toJson())
            johnClient.on(USER_JOIN.event) { args ->
                println("Received user join event: $args")
                val received = args[0] as UserJoined
                if (received.userId != janeId.value) {
                    fail("User id does not match")
                }
            }
            janeClient.emit(JOIN.event, message.toJson())
        }

        val offer = RTCSessionDescription(type = "offer", sdp = "sdp")

        test("should allow client to offer") {
            val message = OfferMessage(johnId.value, janeId.value, offer)
            johnClient.emit(OFFER.event, message.toJson())
        }

        val answer = RTCSessionDescription(type = "answer", sdp = "sdp")

        test("should allow client to answer") {
            val message = AnswerMessage(janeId.value, johnId.value, answer)
            janeClient.emit(ANSWER.event, message.toJson())
        }

        val candidate =
            RTCIceCandidate(
                candidate = "candidate",
                sdpMid = "sdpMid",
                usernameFragment = "usernameFragment",
                sdpMLineIndex = 1
            )
        test("should allow client to send ice candidate") {
            val message = IceCandidateMessage(johnId.value, janeId.value, candidate)
            johnClient.emit(ICE_CANDIDATE.event, message.toJson())
        }
    })
