package piperkt.services.multimedia.interfaces.websockets.server

import base.Test
import io.kotest.core.spec.style.FunSpec
import io.socket.client.IO

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

        test("should start the server") {}
    })
