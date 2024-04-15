package piperkt.services.multimedia.interfaces.websockets.server

import base.UnitTest
import data.UsersData.john
import io.kotest.assertions.nondeterministic.eventually
import io.kotest.assertions.nondeterministic.eventuallyConfig
import io.kotest.matchers.shouldBe
import io.socket.client.IO
import kotlin.time.Duration.Companion.seconds
import mocks.publishers.MockedSessionEventPublisher
import mocks.repositories.InMemorySessionRepository
import piperkt.services.multimedia.application.services.SessionService
import piperkt.services.multimedia.domain.session.SessionFactory
import piperkt.services.multimedia.interfaces.websockets.MultimediaProtocolEvent.*
import piperkt.services.multimedia.interfaces.websockets.MultimediaProtocolMessage
import piperkt.services.multimedia.interfaces.websockets.MultimediaProtocolMessage.*
import piperkt.services.multimedia.interfaces.websockets.MultimediaSocketIOServer
import piperkt.services.multimedia.interfaces.websockets.toJson

class MultimediaSocketIOServerTest :
    UnitTest.FunSpec({
        // Setup
        val sessionRepository = InMemorySessionRepository()
        val sessionService = SessionService(sessionRepository, MockedSessionEventPublisher())
        val server = MultimediaSocketIOServer(sessionService)

        // Setup data
        val userId = john().id
        val session = SessionFactory.fromAllowedUsers(setOf(userId))
        sessionRepository.save(session)

        // Start server
        server.start()
        val authenticatedOptions =
            IO.Options.builder().setExtraHeaders(mapOf("authToken" to listOf(userId.value))).build()
        val client = IO.socket("http://localhost:8888", authenticatedOptions)
        client.connect()

        val eventuallyConfig = eventuallyConfig {
            retries = 10
            duration = 1.seconds
        }

        suspend fun testReceiveMessage(
            event: String,
            serialized: String,
            message: MultimediaProtocolMessage
        ) {
            client.emit(event, serialized)
            eventually(eventuallyConfig) { server.events.last() shouldBe message }
        }

        test("should allow client to join") {
            val message = JoinMessage(session.id.value)
            testReceiveMessage(JOIN.event, message.toJson(), message)
        }

        test("should allow client to offer") {
            val message = OfferMessage("from", "to", "offer")
            testReceiveMessage(OFFER.event, message.toJson(), message)
        }

        test("should allow client to answer") {
            val message = AnswerMessage("from", "to", "answer")
            testReceiveMessage(ANSWER.event, message.toJson(), message)
        }

        test("should allow client to send ice candidate") {
            val message = IceCandidateMessage("from", "to", "candidate")
            testReceiveMessage(ICE_CANDIDATE.event, message.toJson(), message)
        }
    })
