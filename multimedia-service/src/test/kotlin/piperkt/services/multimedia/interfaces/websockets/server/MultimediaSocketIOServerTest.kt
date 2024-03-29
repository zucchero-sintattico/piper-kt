package piperkt.services.multimedia.interfaces.websockets.server

import base.Test
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.socket.client.IO
import mocks.repositories.InMemorySessionRepository
import piperkt.services.multimedia.interfaces.toJson
import piperkt.services.multimedia.interfaces.websockets.api.MultimediaProtocolEvent.JOIN
import piperkt.services.multimedia.interfaces.websockets.api.MultimediaProtocolMessage.*

class MultimediaSocketIOServerTest :
    Test.Unit,
    FunSpec({
        val server = MultimediaSocketIOServer(InMemorySessionRepository())
        server.start()
        val token = "test"
        val authenticatedOptions =
            IO.Options.builder().setExtraHeaders(mapOf("authToken" to listOf(token))).build()
        val client = IO.socket("http://localhost:8888", authenticatedOptions)
        client.connect()
        Thread.sleep(1000)

        test("should allow client to join") {
            val message = JoinMessage("sessionId")
            client.emit(JOIN.event, message.toJson())
            Thread.sleep(1000)
            server.events.last() shouldBe message
        }

        test("should allow client to offer") {
            val message = OfferMessage("from", "to", "offer")
            client.emit("offer", message.toJson())
            Thread.sleep(1000)
            server.events.last() shouldBe message
        }

        test("should allow client to answer") {
            val message = AnswerMessage("from", "to", "answer")
            client.emit("answer", message.toJson())
            Thread.sleep(1000)
            server.events.last() shouldBe message
        }

        test("should allow client to send ice candidate") {
            val message = IceCandidateMessage("from", "to", "candidate")
            client.emit("candidate", message.toJson())
            Thread.sleep(1000)
            server.events.last() shouldBe message
        }
    })
