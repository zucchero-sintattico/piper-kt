package piperkt.services.multimedia.application.usecases

import piperkt.services.multimedia.application.CommandUseCase
import piperkt.services.multimedia.application.failure
import piperkt.services.multimedia.application.success
import piperkt.services.multimedia.application.usecases.AddSessionAllowedUser.Errors.SessionNotFound
import piperkt.services.multimedia.application.usecases.AddSessionAllowedUser.Errors.UserAlreadyAllowed
import piperkt.services.multimedia.domain.SessionId
import piperkt.services.multimedia.domain.SessionRepository
import piperkt.services.multimedia.domain.User
import piperkt.services.multimedia.domain.events.SessionEvent
import piperkt.services.multimedia.domain.events.SessionEventPublisher

open class AddSessionAllowedUser(
    private val sessionRepository: SessionRepository,
    private val sessionEventPublisher: SessionEventPublisher
) : CommandUseCase<AddSessionAllowedUser.Command> {

    data class Command(val sessionId: String, val username: String)

    sealed class Errors : Exception() {
        data class SessionNotFound(val sessionId: String) : Errors()

        data class UserAlreadyAllowed(val sessionId: SessionId, val user: User) : Errors()
    }

    override fun invoke(command: Command): Result<Unit> {
        val session =
            sessionRepository.findById(SessionId(command.sessionId))
                ?: return failure(SessionNotFound(command.sessionId))
        val user = User.fromUsername(command.username)
        if (session.allowedUsers.contains(user))
            return failure(UserAlreadyAllowed(session.id, user))
        sessionRepository.addAllowedUser(session.id, user)
        sessionEventPublisher.publish(SessionEvent.AllowedUserAdded(session.id, user))
        return success()
    }
}
