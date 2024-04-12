package piperkt.services.multimedia.infrastructure.implementation

import jakarta.inject.Singleton
import piperkt.services.multimedia.application.services.SessionService
import piperkt.services.multimedia.domain.session.SessionEventPublisher
import piperkt.services.multimedia.domain.session.SessionRepository

object Services {
    @Singleton
    class SessionServiceImpl(
        sessionRepository: SessionRepository,
        sessionEventPublisher: SessionEventPublisher
    ) : SessionService(sessionRepository, sessionEventPublisher)
}
