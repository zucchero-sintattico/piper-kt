package piperkt.services.multimedia.interfaces.websockets

import base.UnitTest
import data.UsersData.jane
import data.UsersData.john
import io.kotest.assertions.nondeterministic.eventually
import io.kotest.assertions.nondeterministic.eventuallyConfig
import io.kotest.matchers.shouldBe
import io.socket.client.IO
import io.socket.client.Socket
import kotlin.time.Duration.Companion.seconds
import mocks.publishers.MockedSessionEventPublisher
import mocks.repositories.InMemorySessionRepository
import piperkt.services.multimedia.application.session.SessionService
import piperkt.services.multimedia.domain.session.SessionFactory
import piperkt.services.multimedia.interfaces.websockets.MultimediaProtocolEvent.*
import piperkt.services.multimedia.interfaces.websockets.MultimediaProtocolMessage.*

class MultimediaSocketIOServerTest :
    UnitTest.FunSpec({
        // Setup
        val sessionRepository = InMemorySessionRepository()
        val sessionService = SessionService(sessionRepository, MockedSessionEventPublisher())
        val server = MultimediaSocketIOServer(sessionService)

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
            val client = IO.socket("http://localhost:${server.configuration.port}", auth)
            client.connect()
            return client!!
        }

        val johnClient = authorizedClientOf(johnId.value)
        val janeClient = authorizedClientOf(janeId.value)

        val eventuallyConfig = eventuallyConfig {
            retries = 15
            duration = 30.seconds
        }

        test("should allow client to join") {
            val message = JoinMessage(session.id.value)
            johnClient.emit(JOIN.event, message.toJson())
            eventually(eventuallyConfig) { server.events.last() shouldBe message }

            janeClient.emit(JOIN.event, message.toJson())
            eventually(eventuallyConfig) { server.events.last() shouldBe message }
        }

        test("should allow client to offer") {
            val message = OfferMessage(johnId.value, janeId.value, "offer")
            johnClient.emit(OFFER.event, message.toJson())
            eventually(eventuallyConfig) { server.events.last() shouldBe message }
        }

        test("should allow client to answer") {
            val message = AnswerMessage(janeId.value, johnId.value, "answer")
            janeClient.emit(ANSWER.event, message.toJson())
            eventually(eventuallyConfig) { server.events.last() shouldBe message }
        }

        test("should allow client to send ice candidate") {
            val message = IceCandidateMessage(johnId.value, janeId.value, "candidate")
            johnClient.emit(ICE_CANDIDATE.event, message.toJson())
            eventually(eventuallyConfig) { server.events.last() shouldBe message }
        }
    })
