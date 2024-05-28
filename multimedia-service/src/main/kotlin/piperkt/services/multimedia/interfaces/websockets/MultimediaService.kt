package piperkt.services.multimedia.interfaces.websockets

import io.micronaut.serde.ObjectMapper
import piperkt.services.multimedia.application.session.SessionService
import piperkt.services.multimedia.infrastructure.implementation.SocketIOConfiguration

open class MultimediaService(
    sessionService: SessionService,
    objectMapper: ObjectMapper,
    socketIOConfiguration: SocketIOConfiguration,
) {
    private val multimediaSocketIOServer: MultimediaSocketIOServer =
        MultimediaSocketIOServer(sessionService, JsonMapper(objectMapper), socketIOConfiguration)

    init {
        multimediaSocketIOServer.start()
    }
}
