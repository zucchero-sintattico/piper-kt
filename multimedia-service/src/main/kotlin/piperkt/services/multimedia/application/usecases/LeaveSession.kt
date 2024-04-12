package piperkt.services.multimedia.application.usecases

import piperkt.services.multimedia.application.CommandUseCase
import piperkt.services.multimedia.domain.session.SessionErrors
import piperkt.services.multimedia.domain.session.SessionEvent
import piperkt.services.multimedia.domain.session.SessionEventPublisher
import piperkt.services.multimedia.domain.session.SessionId
import piperkt.services.multimedia.domain.session.SessionRepository
import piperkt.services.multimedia.domain.user.Username

/**
 * Leaves a user from a session.
 *
 * @param sessionRepository the repository to access the session
 * @param sessionEventPublisher the event publishers to publish the event
 * @exception SessionErrors.SessionNotFound the session was not found
 * @exception SessionErrors.UserNotParticipant the user is not in the session
 */
open class LeaveSession(
    private val sessionRepository: SessionRepository,
    private val sessionEventPublisher: SessionEventPublisher
) : CommandUseCase<LeaveSession.Command> {

    data class Command(val sessionId: SessionId, val username: Username)

    override fun execute(command: Command) {
        val session =
            sessionRepository.findById(command.sessionId)
                ?: throw SessionErrors.SessionNotFound(command.sessionId)
        session.removeParticipant(command.username)
        sessionRepository.save(session)
        sessionEventPublisher.publish(
            SessionEvent.ParticipantLeft(command.sessionId, command.username)
        )
    }
}
