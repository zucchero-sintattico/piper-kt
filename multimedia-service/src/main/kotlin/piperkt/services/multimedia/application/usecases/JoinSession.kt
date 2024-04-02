package piperkt.services.multimedia.application.usecases

import piperkt.services.multimedia.application.CommandUseCase
import piperkt.services.multimedia.domain.events.SessionEvent.ParticipantJoined
import piperkt.services.multimedia.domain.events.SessionEventPublisher
import piperkt.services.multimedia.domain.session.SessionErrors
import piperkt.services.multimedia.domain.session.SessionId
import piperkt.services.multimedia.domain.session.SessionRepository
import piperkt.services.multimedia.domain.user.UserId

/**
 * Joins a user to a session.
 *
 * @param sessionRepository the repository to access the session
 * @param sessionEventPublisher the event publisher to publish the event
 * @exception SessionErrors.SessionNotFound the session was not found
 * @exception SessionErrors.UserNotAllowed the user is not allowed in the session
 * @exception SessionErrors.UserAlreadyParticipant the user is already a participant in the session
 */
open class JoinSession(
    private val sessionRepository: SessionRepository,
    private val sessionEventPublisher: SessionEventPublisher
) : CommandUseCase<JoinSession.Command> {

    data class Command(val sessionId: SessionId, val userId: UserId)

    override fun validate(command: Command) {
        val session =
            sessionRepository.findById(command.sessionId)
                ?: throw SessionErrors.SessionNotFound(command.sessionId)
        if (!session.allowedUsers().contains(command.userId)) {
            throw SessionErrors.UserNotAllowed(command.sessionId, command.userId)
        }
        if (session.participants().contains(command.userId)) {
            throw SessionErrors.UserAlreadyParticipant(command.sessionId, command.userId)
        }
    }

    override fun execute(command: Command) {
        val session = sessionRepository.findById(command.sessionId)!!
        session.addParticipant(command.userId)
        sessionRepository.save(session)
        sessionEventPublisher.publish(ParticipantJoined(command.sessionId, command.userId))
    }
}
