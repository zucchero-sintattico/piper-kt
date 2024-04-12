package piperkt.services.multimedia.application.services

import piperkt.services.multimedia.application.orThrow
import piperkt.services.multimedia.domain.session.Session
import piperkt.services.multimedia.domain.session.SessionErrors
import piperkt.services.multimedia.domain.session.SessionEvent
import piperkt.services.multimedia.domain.session.SessionEventPublisher
import piperkt.services.multimedia.domain.session.SessionFactory
import piperkt.services.multimedia.domain.session.SessionId
import piperkt.services.multimedia.domain.session.SessionRepository
import piperkt.services.multimedia.domain.user.Username

open class SessionService(
    private val sessionRepository: SessionRepository,
    private val sessionEventPublisher: SessionEventPublisher
) {

    sealed interface Command {

        data class CreateSession(val allowedUsers: Set<Username>) : Command

        data class DeleteSession(val sessionId: SessionId) : Command

        data class AddAllowedUser(val sessionId: SessionId, val username: Username) : Command

        data class RemoveAllowedUser(val sessionId: SessionId, val username: Username) : Command

        data class JoinSession(val sessionId: SessionId, val username: Username) : Command

        data class LeaveSession(val sessionId: SessionId, val username: Username) : Command
    }

    fun createSession(command: Command.CreateSession): Session {
        val session = SessionFactory.fromAllowedUsers(command.allowedUsers)
        sessionRepository.save(session)
        sessionEventPublisher.publish(
            SessionEvent.SessionCreated(session.id, session.allowedUsers())
        )
        return session
    }

    fun deleteSession(command: Command.DeleteSession) {
        sessionRepository.deleteById(command.sessionId)
    }

    fun addAllowedUser(command: Command.AddAllowedUser) {
        val session =
            sessionRepository
                .findById(command.sessionId)
                .orThrow(SessionErrors.SessionNotFound(command.sessionId))
        session.addAllowedUser(command.username)
        sessionRepository.save(session)
    }

    fun removeAllowedUser(command: Command.RemoveAllowedUser) {
        val session =
            sessionRepository
                .findById(command.sessionId)
                .orThrow(SessionErrors.SessionNotFound(command.sessionId))
        session.removeAllowedUser(command.username)
        sessionRepository.save(session)
    }

    fun joinSession(command: Command.JoinSession) {
        val session =
            sessionRepository
                .findById(command.sessionId)
                .orThrow(SessionErrors.SessionNotFound(command.sessionId))
        session.addParticipant(command.username)
        sessionRepository.save(session)
        sessionEventPublisher.publish(
            SessionEvent.ParticipantJoined(command.sessionId, command.username)
        )
    }

    fun leaveSession(command: Command.LeaveSession) {
        val session =
            sessionRepository
                .findById(command.sessionId)
                .orThrow(SessionErrors.SessionNotFound(command.sessionId))
        session.removeParticipant(command.username)
        sessionRepository.save(session)
        sessionEventPublisher.publish(
            SessionEvent.ParticipantLeft(command.sessionId, command.username)
        )
    }
}
