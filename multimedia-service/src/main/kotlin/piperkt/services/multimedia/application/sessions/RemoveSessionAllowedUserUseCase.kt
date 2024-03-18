package piperkt.services.multimedia.application.sessions

import piperkt.services.multimedia.application.CommandUseCase
import piperkt.services.multimedia.application.DomainEvent
import piperkt.services.multimedia.application.EventPublisher
import piperkt.services.multimedia.application.failure
import piperkt.services.multimedia.application.isNull
import piperkt.services.multimedia.application.success
import piperkt.services.multimedia.domain.sessions.SessionId
import piperkt.services.multimedia.domain.sessions.SessionRepository

open class RemoveSessionAllowedUserUseCase(
    private val sessionRepository: SessionRepository,
    private val eventPublisher: EventPublisher
) : CommandUseCase<RemoveSessionAllowedUserUseCase.Command> {

    data class Command(val sessionId: String, val username: String)

    sealed class Events : DomainEvent {
        data class AllowedUserRemoved(val sessionId: String, val username: String) : Events()
    }

    sealed class Errors : Exception() {
        data class SessionNotFound(val sessionId: String) : Errors()
    }

    override fun handle(command: Command): Result<Unit> {
        if (sessionRepository.findById(SessionId(command.sessionId)).isNull())
            return failure(Errors.SessionNotFound(command.sessionId))
        sessionRepository.removeAllowedUser(SessionId(command.sessionId), command.username)
        eventPublisher.publish(Events.AllowedUserRemoved(command.sessionId, command.username))
        return success()
    }
}
