package piperkt.services.multimedia.infrastructure.implementation

import jakarta.inject.Singleton
import piperkt.events.SessionEventPublisher
import piperkt.services.multimedia.application.session.SessionService
import piperkt.services.multimedia.domain.direct.DirectRepository
import piperkt.services.multimedia.domain.server.ServerRepository
import piperkt.services.multimedia.domain.session.SessionRepository

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
}
