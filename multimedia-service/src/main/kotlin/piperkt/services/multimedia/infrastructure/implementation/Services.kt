package piperkt.services.multimedia.infrastructure.implementation

import jakarta.inject.Singleton
import piperkt.services.multimedia.application.MultimediaService
import piperkt.services.multimedia.domain.sessions.SessionRepository

object Services {
    @Singleton
    class MultimediaServiceImpl(
        override val sessionRepository: SessionRepository,
    ) : MultimediaService
}
