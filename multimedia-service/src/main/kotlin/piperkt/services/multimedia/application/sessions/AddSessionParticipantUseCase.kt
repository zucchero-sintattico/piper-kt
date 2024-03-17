package piperkt.services.multimedia.application.sessions

import piperkt.services.multimedia.application.CommandUseCase
import piperkt.services.multimedia.application.DomainEvent
import piperkt.services.multimedia.application.EventPublisher
import piperkt.services.multimedia.application.failure
import piperkt.services.multimedia.application.isNull
import piperkt.services.multimedia.application.success
import piperkt.services.multimedia.domain.sessions.SessionId
import piperkt.services.multimedia.domain.sessions.SessionRepository

open class AddSessionParticipantUseCase(
    private val sessionRepository: SessionRepository,
    private val eventPublisher: EventPublisher
) : CommandUseCase<AddSessionParticipantUseCase.Command> {

    data class Command(val sessionId: String, val username: String)

    sealed class Events : DomainEvent {
        data class ParticipantAdded(val sessionId: String, val username: String) : Events()
    }

    sealed class Errors : Exception() {
        data class SessionNotFound(val sessionId: String) : Errors()
    }

    override fun handle(command: Command): Result<Unit> {
        if (sessionRepository.findById(SessionId(command.sessionId)).isNull())
            return failure(Errors.SessionNotFound(command.sessionId))
        sessionRepository.addParticipant(SessionId(command.sessionId), command.username)
        eventPublisher.publish(Events.ParticipantAdded(command.sessionId, command.username))
        return success()
    }
}
