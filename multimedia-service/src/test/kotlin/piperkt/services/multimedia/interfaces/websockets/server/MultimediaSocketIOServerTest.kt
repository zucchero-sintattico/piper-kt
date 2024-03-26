package piperkt.services.multimedia.interfaces.websockets.server

import base.Test
import io.kotest.core.spec.style.FunSpec

class MultimediaSocketIOServerTest :
    Test.Unit,
    FunSpec({
        val server = MultimediaSocketIOServer(SocketIOConfiguration())
        test("should start server") { server.start() }
    })
