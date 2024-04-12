package piperkt.services.multimedia.application.usecases

import piperkt.services.multimedia.application.CommandUseCase
import piperkt.services.multimedia.application.orThrow
import piperkt.services.multimedia.domain.session.SessionErrors
import piperkt.services.multimedia.domain.session.SessionEvent.ParticipantJoined
import piperkt.services.multimedia.domain.session.SessionEventPublisher
import piperkt.services.multimedia.domain.session.SessionId
import piperkt.services.multimedia.domain.session.SessionRepository
import piperkt.services.multimedia.domain.user.Username

/**
 * Joins a user to a session.
 *
 * @param sessionRepository the repository to access the session
 * @param sessionEventPublisher the event publishers to publish the event
 * @exception SessionErrors.SessionNotFound the session was not found
 * @exception SessionErrors.UserNotAllowed the user is not allowed in the session
 * @exception SessionErrors.UserAlreadyParticipant the user is already a participant in the session
 */
open class JoinSession(
    private val sessionRepository: SessionRepository,
    private val sessionEventPublisher: SessionEventPublisher
) : CommandUseCase<JoinSession.Command> {

    data class Command(val sessionId: SessionId, val username: Username)

    override fun execute(command: Command) {
        val session =
            sessionRepository
                .findById(command.sessionId)
                .orThrow(SessionErrors.SessionNotFound(command.sessionId))
        session.addParticipant(command.username)
        sessionRepository.save(session)
        sessionEventPublisher.publish(ParticipantJoined(command.sessionId, command.username))
    }
}
