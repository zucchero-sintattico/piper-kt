package piperkt.services.multimedia.application.usecases

import piperkt.services.multimedia.application.CommandUseCase
import piperkt.services.multimedia.application.failure
import piperkt.services.multimedia.application.isNull
import piperkt.services.multimedia.application.success
import piperkt.services.multimedia.domain.SessionId
import piperkt.services.multimedia.domain.SessionRepository
import piperkt.services.multimedia.domain.User
import piperkt.services.multimedia.domain.events.SessionEvent
import piperkt.services.multimedia.domain.events.SessionEventPublisher

open class RemoveSessionAllowedUser(
    private val sessionRepository: SessionRepository,
    private val sessionEventPublisher: SessionEventPublisher
) : CommandUseCase<RemoveSessionAllowedUser.Command> {

    data class Command(val sessionId: String, val username: String)

    sealed class Errors : Exception() {
        data class SessionNotFound(val sessionId: String) : Errors()

        data class UserNotInSession(val sessionId: String, val username: String) : Errors()
    }

    override fun invoke(command: Command): Result<Unit> {
        if (sessionRepository.findById(SessionId(command.sessionId)).isNull())
            return failure(Errors.SessionNotFound(command.sessionId))
        val removed =
            sessionRepository.removeAllowedUser(
                SessionId(command.sessionId),
                User.fromUsername(command.username)
            )
        if (!removed) return failure(Errors.UserNotInSession(command.sessionId, command.username))
        sessionEventPublisher.publish(
            SessionEvent.AllowedUserRemoved(
                SessionId(command.sessionId),
                User.fromUsername(command.username)
            )
        )
        return success()
    }
}
