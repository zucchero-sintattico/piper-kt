package piperkt.services.multimedia.application.usecases.internal

import piperkt.services.multimedia.application.CommandUseCase
import piperkt.services.multimedia.application.usecases.internal.AddSessionAllowedUser.Command
import piperkt.services.multimedia.domain.events.SessionEvent.AllowedUserAdded
import piperkt.services.multimedia.domain.events.SessionEventPublisher
import piperkt.services.multimedia.domain.session.SessionErrors
import piperkt.services.multimedia.domain.session.SessionId
import piperkt.services.multimedia.domain.session.SessionRepository
import piperkt.services.multimedia.domain.user.UserId

/**
 * Adds a user to the list of allowed users in a session.
 *
 * @param sessionRepository the repository to access the session
 * @param sessionEventPublisher the event publisher to publish the event
 * @exception SessionErrors.SessionNotFound the session was not found
 * @exception SessionErrors.UserAlreadyAllowed the user is already allowed in the session
 */
open class AddSessionAllowedUser(
    private val sessionRepository: SessionRepository,
    private val sessionEventPublisher: SessionEventPublisher
) : CommandUseCase<Command> {

    /**
     * The command to add a user to the list of allowed users in a session.
     *
     * @param sessionId the session id
     * @param userId the user id
     */
    data class Command(val sessionId: SessionId, val userId: UserId)

    override fun validate(command: Command) {
        val session =
            sessionRepository.findById(command.sessionId)
                ?: throw SessionErrors.SessionNotFound(command.sessionId)
        if (session.allowedUsersId().contains(command.userId)) {
            throw SessionErrors.UserAlreadyAllowed(command.sessionId, command.userId)
        }
    }

    override fun execute(command: Command) {
        val session = sessionRepository.findById(command.sessionId)!!
        session.addAllowedUser(command.userId)
        sessionRepository.save(session)
        sessionEventPublisher.publish(AllowedUserAdded(command.sessionId, command.userId))
    }
}
