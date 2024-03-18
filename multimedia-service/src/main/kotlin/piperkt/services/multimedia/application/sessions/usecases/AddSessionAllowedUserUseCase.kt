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

open class AddSessionAllowedUserUseCase(
    private val sessionRepository: SessionRepository,
    private val eventPublisher: EventPublisher
) : CommandUseCase<AddSessionAllowedUserUseCase.Command> {

    data class Command(val sessionId: String, val username: String)

    sealed class Events : DomainEvent {
        data class AllowedUserAdded(val sessionId: SessionId, val user: User) : Events()
    }

    sealed class Errors : Exception() {
        data class SessionNotFound(val sessionId: String) : Errors()

        data class UserAlreadyAllowed(val sessionId: SessionId, val user: User) : Errors()
    }

    override fun handle(command: Command): Result<Unit> {
        if (sessionRepository.findById(SessionId(command.sessionId)).isNull())
            return failure(Errors.SessionNotFound(command.sessionId))
        val added =
            sessionRepository.addAllowedUser(
                SessionId(command.sessionId),
                User.fromUsername(command.username)
            )
        if (!added)
            return failure(
                Errors.UserAlreadyAllowed(
                    SessionId(command.sessionId),
                    User.fromUsername(command.username)
                )
            )
        eventPublisher.publish(
            Events.AllowedUserAdded(
                SessionId(command.sessionId),
                User.fromUsername(command.username)
            )
        )
        return success()
    }
}
