package piperkt.services.multimedia.application.sessions.usecases

import piperkt.services.multimedia.application.CommandUseCase
import piperkt.services.multimedia.application.DomainEvent
import piperkt.services.multimedia.application.EventPublisher
import piperkt.services.multimedia.application.asFailure
import piperkt.services.multimedia.application.success
import piperkt.services.multimedia.domain.sessions.SessionId
import piperkt.services.multimedia.domain.sessions.SessionRepository

open class DeleteSessionUseCase(
    private val sessionRepository: SessionRepository,
    private val eventPublisher: EventPublisher
) : CommandUseCase<DeleteSessionUseCase.Command> {

    data class Command(val sessionId: String)

    sealed class Events : DomainEvent {
        data class SessionDeleted(val sessionId: SessionId) : Events()
    }

    sealed class Errors : Exception() {
        data class SessionNotFound(val sessionId: String) : Errors()
    }

    override fun handle(command: Command): Result<Unit> {
        if (!sessionRepository.exists(command.sessionId)) {
            return Errors.SessionNotFound(command.sessionId).asFailure()
        }
        sessionRepository.deleteSession(command.sessionId)
        eventPublisher.publish(Events.SessionDeleted(SessionId(command.sessionId)))
        return success()
    }
}
