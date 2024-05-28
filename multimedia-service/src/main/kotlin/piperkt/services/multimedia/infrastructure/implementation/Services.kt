package piperkt.services.multimedia.infrastructure.implementation

import io.micronaut.context.annotation.Context
import io.micronaut.serde.ObjectMapper
import jakarta.inject.Singleton
import piperkt.events.SessionEventPublisher
import piperkt.services.multimedia.application.session.SessionService
import piperkt.services.multimedia.domain.direct.DirectRepository
import piperkt.services.multimedia.domain.server.ServerRepository
import piperkt.services.multimedia.domain.session.SessionRepository
import piperkt.services.multimedia.interfaces.websockets.JsonMapper
import piperkt.services.multimedia.interfaces.websockets.MultimediaSocketIOServer

object Services {
    @Singleton
    class SessionServiceImpl(
        sessionRepository: SessionRepository,
        serverRepository: ServerRepository,
        directRepository: DirectRepository,
        sessionEventPublisher: SessionEventPublisher,
    ) :
        SessionService(
            sessionRepository,
            serverRepository,
            directRepository,
            sessionEventPublisher
        )

    @Context
    class MultimediaServiceImpl(
        sessionService: SessionService,
        objectMapper: ObjectMapper,
        socketIOConfiguration: SocketIOConfiguration,
    ) : MultimediaSocketIOServer(sessionService, JsonMapper(objectMapper), socketIOConfiguration)
}
