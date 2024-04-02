package piperkt.services.multimedia.application.usecases

import piperkt.services.multimedia.application.CommandUseCase
import piperkt.services.multimedia.domain.events.SessionEvent
import piperkt.services.multimedia.domain.events.SessionEventPublisher
import piperkt.services.multimedia.domain.session.SessionErrors
import piperkt.services.multimedia.domain.session.SessionId
import piperkt.services.multimedia.domain.session.SessionRepository
import piperkt.services.multimedia.domain.user.UserId

/**
 * Leaves a user from a session.
 *
 * @param sessionRepository the repository to access the session
 * @param sessionEventPublisher the event publisher to publish the event
 * @exception SessionErrors.SessionNotFound the session was not found
 * @exception SessionErrors.UserNotParticipant the user is not in the session
 */
open class LeaveSession(
    private val sessionRepository: SessionRepository,
    private val sessionEventPublisher: SessionEventPublisher
) : CommandUseCase<LeaveSession.Command> {

    data class Command(val sessionId: SessionId, val userId: UserId)

    override fun validate(command: Command) {
        val session =
            sessionRepository.findById(command.sessionId)
                ?: throw SessionErrors.SessionNotFound(command.sessionId)
        if (!session.participants().contains(command.userId)) {
            throw SessionErrors.UserNotParticipant(command.sessionId, command.userId)
        }
    }

    override fun execute(command: Command) {
        val session = sessionRepository.findById(command.sessionId)!!
        session.removeParticipant(command.userId)
        sessionRepository.save(session)
        sessionEventPublisher.publish(
            SessionEvent.ParticipantLeft(command.sessionId, command.userId)
        )
    }
}