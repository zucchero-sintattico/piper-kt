package piperkt.services.multimedia.application.usecases

import piperkt.services.multimedia.application.CommandUseCase
import piperkt.services.multimedia.application.asFailure
import piperkt.services.multimedia.application.success
import piperkt.services.multimedia.domain.SessionId
import piperkt.services.multimedia.domain.SessionRepository
import piperkt.services.multimedia.domain.events.SessionEvent
import piperkt.services.multimedia.domain.events.SessionEventPublisher

open class DeleteSession(
    private val sessionRepository: SessionRepository,
    private val sessionEventPublisher: SessionEventPublisher
) : CommandUseCase<DeleteSession.Command> {

    data class Command(val sessionId: String)

    sealed class Errors : Exception() {
        data class SessionNotFound(val sessionId: String) : Errors()
    }

    override fun invoke(command: Command): Result<Unit> {
        if (!sessionRepository.exists(command.sessionId)) {
            return Errors.SessionNotFound(command.sessionId).asFailure()
        }
        sessionRepository.deleteSession(command.sessionId)
        sessionEventPublisher.publish(SessionEvent.SessionDeleted(SessionId(command.sessionId)))
        return success()
    }
}
