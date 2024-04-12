package piperkt.services.multimedia.infrastructure.implementation

import jakarta.inject.Singleton
import piperkt.services.multimedia.application.usecases.GetSessionParticipants
import piperkt.services.multimedia.application.usecases.JoinSession
import piperkt.services.multimedia.application.usecases.LeaveSession
import piperkt.services.multimedia.domain.session.SessionEventPublisher
import piperkt.services.multimedia.domain.session.SessionRepository

object UseCases {

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
}
