package piperkt.services.multimedia.interfaces.websockets.server

import base.Test
import io.kotest.core.spec.style.FunSpec
import io.socket.client.IO

class MultimediaSocketIOServerTest :
    Test.Unit,
    FunSpec({
        val server = MultimediaSocketIOServer(SocketIOConfiguration())
        server.start()
        val client = IO.socket("http://localhost:8888")
        client.connect()

        test("should handle join message") { client.emit("join", "john") }
    })
