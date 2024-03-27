package piperkt.services.multimedia.interfaces.websockets.server

import base.Test
import io.kotest.core.spec.style.FunSpec
import io.socket.client.IO
import kotlinx.serialization.json.Json

class MultimediaSocketIOServerTest :
    Test.Unit,
    FunSpec({
        val server = MultimediaSocketIOServer(SocketIOConfiguration())
        server.start()
        val client = IO.socket("http://localhost:8888")
        client.connect()

        test("should handle join message") {
            val message = JoinMessage("username", "content")
            client.emit("join", Json.encodeToString(JoinMessage.serializer(), message))
        }
    })
