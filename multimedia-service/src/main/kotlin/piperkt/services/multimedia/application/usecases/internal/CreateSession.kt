package piperkt.services.multimedia.application.usecases.internal

import piperkt.services.multimedia.application.CommandUseCase
import piperkt.services.multimedia.domain.events.SessionEvent.SessionCreated
import piperkt.services.multimedia.domain.events.SessionEventPublisher
import piperkt.services.multimedia.domain.session.SessionFactory
import piperkt.services.multimedia.domain.session.SessionRepository
import piperkt.services.multimedia.domain.user.UserId

/**
 * Creates a new session.
 *
 * @param sessionRepository the repository to access the session
 * @param sessionEventPublisher the event publisher to publish the event
 */
open class CreateSession(
    private val sessionRepository: SessionRepository,
    private val sessionEventPublisher: SessionEventPublisher
) : CommandUseCase<CreateSession.Command> {

    /**
     * The command to create a new session.
     *
     * @param allowedUserIds the list of user ids allowed in the session
     */
    data class Command(val allowedUserIds: Set<UserId>)

    override fun validate(command: Command) = Unit // No validation needed

    override fun execute(command: Command) {
        val session = SessionFactory.fromAllowedUsersIds(command.allowedUserIds)
        sessionRepository.save(session)
        sessionEventPublisher.publish(SessionCreated(session.id, session.allowedUsersId()))
    }
}
