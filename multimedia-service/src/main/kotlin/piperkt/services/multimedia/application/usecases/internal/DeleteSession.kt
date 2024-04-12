package piperkt.services.multimedia.application.usecases.internal

import piperkt.services.multimedia.application.CommandUseCase
import piperkt.services.multimedia.domain.session.SessionErrors
import piperkt.services.multimedia.domain.session.SessionEvent
import piperkt.services.multimedia.domain.session.SessionEventPublisher
import piperkt.services.multimedia.domain.session.SessionId
import piperkt.services.multimedia.domain.session.SessionRepository

/**
 * Deletes a session.
 *
 * @param sessionRepository the repository to access the session
 * @param sessionEventPublisher the event publishers to publish the event
 * @exception SessionNotFound the session was not found
 */
open class DeleteSession(
    private val sessionRepository: SessionRepository,
    private val sessionEventPublisher: SessionEventPublisher
) : CommandUseCase<DeleteSession.Command> {

    /**
     * The command to delete a session.
     *
     * @param sessionId the session id
     */
    data class Command(val sessionId: SessionId)

    override fun execute(command: Command) {
        sessionRepository.findById(command.sessionId)
            ?: throw SessionErrors.SessionNotFound(command.sessionId)
        sessionRepository.deleteById(command.sessionId)
        sessionEventPublisher.publish(SessionEvent.SessionDeleted(command.sessionId))
    }
}
