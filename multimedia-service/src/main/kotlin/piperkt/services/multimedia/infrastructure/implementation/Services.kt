package piperkt.services.multimedia.infrastructure.implementation

import jakarta.inject.Singleton
import piperkt.services.multimedia.application.direct.DirectService
import piperkt.services.multimedia.application.server.ServerService
import piperkt.services.multimedia.application.session.SessionService
import piperkt.services.multimedia.domain.direct.DirectRepository
import piperkt.services.multimedia.domain.server.ServerRepository
import piperkt.services.multimedia.domain.session.SessionEventPublisher
import piperkt.services.multimedia.domain.session.SessionRepository

object Services {
    @Singleton
    class SessionServiceImpl(
        sessionRepository: SessionRepository,
        serverRepository: ServerRepository,
        directRepository: DirectRepository,
        sessionEventPublisher: SessionEventPublisher
    ) :
        SessionService(
            sessionRepository,
            serverRepository,
            directRepository,
            sessionEventPublisher
        )

    @Singleton
    class DirectServiceImpl(directRepository: DirectRepository) : DirectService(directRepository)

    @Singleton
    class ServerServiceImpl(serverRepository: ServerRepository) : ServerService(serverRepository)
}
