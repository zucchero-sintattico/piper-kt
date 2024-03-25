package piperkt.services.multimedia.application.usecases

import piperkt.services.multimedia.application.CommandUseCase
import piperkt.services.multimedia.application.success
import piperkt.services.multimedia.domain.SessionRepository
import piperkt.services.multimedia.domain.events.SessionEvent
import piperkt.services.multimedia.domain.events.SessionEventPublisher

open class CreateSession(
    private val sessionRepository: SessionRepository,
    private val sessionEventPublisher: SessionEventPublisher
) : CommandUseCase<CreateSession.Command> {

    data class Command(val allowedUsers: List<String>)

    override fun invoke(command: Command): Result<Unit> {
        val session = sessionRepository.createSession(command.allowedUsers)
        sessionEventPublisher.publish(SessionEvent.SessionCreated(session.id, session.allowedUsers))
        return success()
    }
}
