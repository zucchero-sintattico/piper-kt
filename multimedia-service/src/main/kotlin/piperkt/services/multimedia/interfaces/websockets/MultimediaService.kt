package piperkt.services.multimedia.interfaces.websockets

import piperkt.services.multimedia.application.session.SessionService

open class MultimediaService(
    sessionService: SessionService,
    jsonMapper: JsonMapper,
    socketIOConfiguration: SocketIOConfiguration,
) {
    private val multimediaSocketIOServer: MultimediaSocketIOServer =
        MultimediaSocketIOServer(sessionService, jsonMapper, socketIOConfiguration)

    init {
        multimediaSocketIOServer.start()
    }
}
