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

open class AddSessionParticipantUseCase(
    private val sessionRepository: SessionRepository,
    private val eventPublisher: EventPublisher
) : CommandUseCase<AddSessionParticipantUseCase.Command> {

    data class Command(val sessionId: String, val username: String)

    sealed class Events : DomainEvent {
        data class ParticipantAdded(val sessionId: SessionId, val user: User) : Events()
    }

    sealed class Errors : Exception() {
        data class SessionNotFound(val sessionId: String) : Errors()

        data class UserAlreadyParticipant(val sessionId: String, val username: String) : Errors()
    }

    override fun handle(command: Command): Result<Unit> {
        if (sessionRepository.findById(SessionId(command.sessionId)).isNull())
            return failure(Errors.SessionNotFound(command.sessionId))
        val added =
            sessionRepository.addParticipant(
                SessionId(command.sessionId),
                User.fromUsername(command.username)
            )
        if (!added) return failure(Errors.SessionNotFound(command.sessionId))
        eventPublisher.publish(
            Events.ParticipantAdded(
                SessionId(command.sessionId),
                User.fromUsername(command.username)
            )
        )
        return success()
    }
}
