package piperkt.services.multimedia.application.sessions.usecases

import piperkt.services.multimedia.application.CommandUseCase
import piperkt.services.multimedia.application.DomainEvent
import piperkt.services.multimedia.application.EventPublisher
import piperkt.services.multimedia.application.success
import piperkt.services.multimedia.domain.sessions.Session
import piperkt.services.multimedia.domain.sessions.SessionRepository

open class CreateSessionUseCase(
    private val sessionRepository: SessionRepository,
    private val eventPublisher: EventPublisher
) : CommandUseCase<CreateSessionUseCase.Command> {

    data class Command(val allowedUsers: List<String>)

    sealed class Events : DomainEvent {
        data class SessionCreated(val session: Session) : Events()
    }

    override fun handle(command: Command): Result<Unit> {
        val session = sessionRepository.createSession(command.allowedUsers)
        eventPublisher.publish(Events.SessionCreated(session))
        return success()
    }
}
