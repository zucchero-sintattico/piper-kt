package piperkt.services.multimedia.interfaces.websockets.server

import base.Test
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.socket.client.IO
import piperkt.services.multimedia.interfaces.toJson
import piperkt.services.multimedia.interfaces.websockets.api.MultimediaProtocolMessage
import piperkt.services.multimedia.interfaces.websockets.api.MultimediaProtocolMessage.JoinMessage

class MultimediaSocketIOServerTest :
    Test.Unit,
    FunSpec({
        val server = MultimediaSocketIOServer()
        server.start()
        val client =
            IO.socket(
                "http://localhost:8888",
                IO.Options.builder().setExtraHeaders(mapOf("authToken" to listOf("test"))).build()
            )
        client.connect()
        Thread.sleep(1000)

        test("should allow client to join") {
            val message = JoinMessage("sessionId")
            client.emit("join", message.toJson())
            Thread.sleep(1000)
            server.events.last() shouldBe message
        }

        test("should allow client to offer") {
            val message = MultimediaProtocolMessage.OfferMessage("from", "to", "offer")
            client.emit("offer", message.toJson())
            Thread.sleep(1000)
            server.events.last() shouldBe message
        }

        test("should allow client to answer") {
            val message = MultimediaProtocolMessage.AnswerMessage("from", "to", "answer")
            client.emit("answer", message.toJson())
            Thread.sleep(1000)
            server.events.last() shouldBe message
        }

        test("should allow client to send ice candidate") {
            val message = MultimediaProtocolMessage.IceCandidateMessage("from", "to", "candidate")
            client.emit("candidate", message.toJson())
            Thread.sleep(1000)
            server.events.last() shouldBe message
        }
    })
