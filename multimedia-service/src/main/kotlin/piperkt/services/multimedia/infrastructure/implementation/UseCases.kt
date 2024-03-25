package piperkt.services.multimedia.infrastructure.implementation

import jakarta.inject.Singleton
import piperkt.services.multimedia.application.EventPublisher
import piperkt.services.multimedia.application.usecases.AddSessionAllowedUserUseCase
import piperkt.services.multimedia.application.usecases.AddSessionParticipantUseCase
import piperkt.services.multimedia.application.usecases.CreateSessionUseCase
import piperkt.services.multimedia.application.usecases.DeleteSessionUseCase
import piperkt.services.multimedia.application.usecases.GetSessionParticipantsUseCase
import piperkt.services.multimedia.application.usecases.RemoveSessionAllowedUserUseCase
import piperkt.services.multimedia.application.usecases.RemoveSessionParticipantUseCase
import piperkt.services.multimedia.domain.SessionRepository

object UseCases {

    @Singleton
    class CreateSessionUseCaseService(
        sessionRepository: SessionRepository,
        eventPublisher: EventPublisher
    ) : CreateSessionUseCase(sessionRepository, eventPublisher)

    @Singleton
    class DeleteSessionUseCaseService(
        sessionRepository: SessionRepository,
        eventPublisher: EventPublisher
    ) : DeleteSessionUseCase(sessionRepository, eventPublisher)

    @Singleton
    class GetSessionParticipantsUseCaseService(sessionRepository: SessionRepository) :
        GetSessionParticipantsUseCase(sessionRepository)

    @Singleton
    class AddSessionParticipantUseCaseService(
        sessionRepository: SessionRepository,
        eventPublisher: EventPublisher
    ) : AddSessionParticipantUseCase(sessionRepository, eventPublisher)

    @Singleton
    class RemoveSessionParticipantUseCaseService(
        sessionRepository: SessionRepository,
        eventPublisher: EventPublisher
    ) : RemoveSessionParticipantUseCase(sessionRepository, eventPublisher)

    @Singleton
    class AddSessionAllowedUserUseCaseService(
        sessionRepository: SessionRepository,
        eventPublisher: EventPublisher
    ) : AddSessionAllowedUserUseCase(sessionRepository, eventPublisher)

    @Singleton
    class RemoveSessionAllowedUserUseCaseService(
        sessionRepository: SessionRepository,
        eventPublisher: EventPublisher
    ) : RemoveSessionAllowedUserUseCase(sessionRepository, eventPublisher)
}
