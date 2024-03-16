package piperkt.services.multimedia.infrastructure.implementation

import jakarta.inject.Singleton
import piperkt.services.multimedia.application.sessions.GetUsersInSessionUseCase
import piperkt.services.multimedia.domain.sessions.SessionRepository

object UseCases {
    @Singleton
    class GetUsersInSessionUseCaseService(sessionRepository: SessionRepository) :
        GetUsersInSessionUseCase(sessionRepository)
}
