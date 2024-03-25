package piperkt.services.multimedia.infrastructure.implementation

import jakarta.inject.Singleton
import piperkt.services.multimedia.application.usecases.AddSessionAllowedUser
import piperkt.services.multimedia.application.usecases.CreateSession
import piperkt.services.multimedia.application.usecases.DeleteSession
import piperkt.services.multimedia.application.usecases.GetSessionParticipants
import piperkt.services.multimedia.application.usecases.JoinSession
import piperkt.services.multimedia.application.usecases.LeaveSession
import piperkt.services.multimedia.application.usecases.RemoveSessionAllowedUser
import piperkt.services.multimedia.domain.SessionRepository
import piperkt.services.multimedia.domain.events.SessionEventPublisher

object UseCases {

    @Singleton
    class CreateSessionUseCaseService(
        sessionRepository: SessionRepository,
        sessionEventPublisher: SessionEventPublisher,
    ) : CreateSession(sessionRepository, sessionEventPublisher)

    @Singleton
    class DeleteSessionService(
        sessionRepository: SessionRepository,
        sessionEventPublisher: SessionEventPublisher,
    ) : DeleteSession(sessionRepository, sessionEventPublisher)

    @Singleton
    class GetSessionParticipantsService(sessionRepository: SessionRepository) :
        GetSessionParticipants(sessionRepository)

    @Singleton
    class JoinSessionService(
        sessionRepository: SessionRepository,
        sessionEventPublisher: SessionEventPublisher
    ) : JoinSession(sessionRepository, sessionEventPublisher)

    @Singleton
    class LeaveSessionService(
        sessionRepository: SessionRepository,
        sessionEventPublisher: SessionEventPublisher
    ) : LeaveSession(sessionRepository, sessionEventPublisher)

    @Singleton
    class AddSessionAllowedUserService(
        sessionRepository: SessionRepository,
        sessionEventPublisher: SessionEventPublisher
    ) : AddSessionAllowedUser(sessionRepository, sessionEventPublisher)

    @Singleton
    class RemoveSessionAllowedUserService(
        sessionRepository: SessionRepository,
        sessionEventPublisher: SessionEventPublisher
    ) : RemoveSessionAllowedUser(sessionRepository, sessionEventPublisher)
}
