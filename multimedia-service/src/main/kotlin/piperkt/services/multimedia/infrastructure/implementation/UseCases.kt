package piperkt.services.multimedia.infrastructure.implementation

import jakarta.inject.Singleton
import piperkt.services.multimedia.application.sessions.usecases.GetSessionParticipantsUseCase
import piperkt.services.multimedia.domain.sessions.SessionRepository

object UseCases {
    @Singleton
    class GetSessionParticipantsUseCaseService(sessionRepository: SessionRepository) :
        GetSessionParticipantsUseCase(sessionRepository)
}
