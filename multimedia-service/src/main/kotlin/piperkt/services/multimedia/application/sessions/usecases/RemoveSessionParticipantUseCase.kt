package piperkt.services.multimedia.application.sessions.usecases

import piperkt.services.multimedia.application.CommandUseCase
import piperkt.services.multimedia.application.DomainEvent
import piperkt.services.multimedia.application.EventPublisher
import piperkt.services.multimedia.application.failure
import piperkt.services.multimedia.application.isNull
import piperkt.services.multimedia.application.success
import piperkt.services.multimedia.domain.sessions.SessionId
import piperkt.services.multimedia.domain.sessions.SessionRepository
import piperkt.services.multimedia.domain.users.User

open class RemoveSessionParticipantUseCase(
    private val sessionRepository: SessionRepository,
    private val eventPublisher: EventPublisher
) : CommandUseCase<RemoveSessionParticipantUseCase.Command> {

    data class Command(val sessionId: String, val username: String)

    sealed class Events : DomainEvent {
        data class ParticipantRemoved(val sessionId: SessionId, val user: User) : Events()
    }

    sealed class Errors : Exception() {
        data class SessionNotFound(val sessionId: String) : Errors()

        data class UserNotInSession(val sessionId: String, val username: String) : Errors()
    }

    override fun invoke(command: Command): Result<Unit> {
        if (sessionRepository.findById(SessionId(command.sessionId)).isNull())
            return failure(Errors.SessionNotFound(command.sessionId))
        val removed =
            sessionRepository.removeParticipant(
                SessionId(command.sessionId),
                User.fromUsername(command.username)
            )
        if (!removed) return failure(Errors.UserNotInSession(command.sessionId, command.username))
        eventPublisher.publish(
            Events.ParticipantRemoved(
                SessionId(command.sessionId),
                User.fromUsername(command.username)
            )
        )
        return success()
    }
}
