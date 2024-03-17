package piperkt.services.multimedia.infrastructure.implementation

import jakarta.inject.Singleton
import piperkt.services.multimedia.application.sessions.GetSessionParticipantsUseCase
import piperkt.services.multimedia.domain.sessions.SessionRepository

object UseCases {
    @Singleton
    class GetSessionParticipantsUseCaseService(sessionRepository: SessionRepository) :
        GetSessionParticipantsUseCase(sessionRepository)
}
