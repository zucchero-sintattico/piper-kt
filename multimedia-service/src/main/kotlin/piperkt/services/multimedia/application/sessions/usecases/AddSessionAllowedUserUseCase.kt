package piperkt.services.multimedia.application.sessions.usecases

import piperkt.services.multimedia.application.CommandUseCase
import piperkt.services.multimedia.application.DomainEvent
import piperkt.services.multimedia.application.EventPublisher
import piperkt.services.multimedia.application.failure
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
        val user = User.fromUsername(command.username)
        val session =
            sessionRepository.findById(SessionId(command.sessionId))
                ?: return failure(Errors.SessionNotFound(command.sessionId))
        if (session.allowedUsers.contains(user))
            return failure(Errors.UserAlreadyAllowed(session.id, user))
        sessionRepository.addAllowedUser(session.id, user)
        eventPublisher.publish(Events.AllowedUserAdded(session.id, user))
        return success()
    }
}
