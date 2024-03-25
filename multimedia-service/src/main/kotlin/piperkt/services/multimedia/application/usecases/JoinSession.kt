package piperkt.services.multimedia.application.usecases

import piperkt.services.multimedia.application.CommandUseCase
import piperkt.services.multimedia.application.failure
import piperkt.services.multimedia.application.success
import piperkt.services.multimedia.domain.SessionId
import piperkt.services.multimedia.domain.SessionRepository
import piperkt.services.multimedia.domain.User
import piperkt.services.multimedia.domain.events.SessionEvent
import piperkt.services.multimedia.domain.events.SessionEventPublisher

open class JoinSession(
    private val sessionRepository: SessionRepository,
    private val sessionEventPublisher: SessionEventPublisher
) : CommandUseCase<JoinSession.Command> {

    data class Command(val sessionId: String, val username: String)

    sealed class Errors : Exception() {
        data class SessionNotFound(val sessionId: String) : Errors()

        data class UserNotAllowed(val sessionId: String, val username: String) : Errors()

        data class UserAlreadyParticipant(val sessionId: String, val username: String) : Errors()
    }

    override fun invoke(command: Command): Result<Unit> {
        val session =
            sessionRepository.findById(SessionId(command.sessionId))
                ?: return failure(Errors.SessionNotFound(command.sessionId))
        if (!session.allowedUsers.contains(User.fromUsername(command.username)))
            return failure(Errors.UserNotAllowed(command.sessionId, command.username))
        if (session.participants.contains(User.fromUsername(command.username)))
            return failure(Errors.UserAlreadyParticipant(command.sessionId, command.username))
        sessionRepository.addParticipant(
            SessionId(command.sessionId),
            User.fromUsername(command.username)
        )
        sessionEventPublisher.publish(
            SessionEvent.ParticipantJoined(
                SessionId(command.sessionId),
                User.fromUsername(command.username)
            )
        )
        return success()
    }
}
