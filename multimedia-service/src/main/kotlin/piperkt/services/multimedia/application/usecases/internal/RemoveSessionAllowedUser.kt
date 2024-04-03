package piperkt.services.multimedia.application.usecases.internal

import piperkt.services.multimedia.application.CommandUseCase
import piperkt.services.multimedia.domain.session.SessionErrors
import piperkt.services.multimedia.domain.session.SessionEvent.AllowedUserRemoved
import piperkt.services.multimedia.domain.session.SessionEventPublisher
import piperkt.services.multimedia.domain.session.SessionId
import piperkt.services.multimedia.domain.session.SessionRepository
import piperkt.services.multimedia.domain.user.Username

/**
 * Removes a user from the allowed users in a session.
 *
 * @param sessionRepository the repository to access the session
 * @param sessionEventPublisher the event publishers to publish the event
 * @exception SessionErrors.SessionNotFound the session was not found
 * @exception SessionErrors.UserNotAllowed the user is not in the session
 */
open class RemoveSessionAllowedUser(
    private val sessionRepository: SessionRepository,
    private val sessionEventPublisher: SessionEventPublisher
) : CommandUseCase<RemoveSessionAllowedUser.Command> {

    data class Command(val sessionId: SessionId, val username: Username)

    override fun validate(command: Command) {
        val session =
            sessionRepository.findById(command.sessionId)
                ?: throw SessionErrors.SessionNotFound(command.sessionId)
        if (!session.allowedUsers().contains(command.username)) {
            throw SessionErrors.UserNotAllowed(command.sessionId, command.username)
        }
    }

    override fun execute(command: Command) {
        val session = sessionRepository.findById(command.sessionId)!!
        session.removeAllowedUser(command.username)
        sessionRepository.save(session)
        sessionEventPublisher.publish(AllowedUserRemoved(command.sessionId, command.username))
    }
}
