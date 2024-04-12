package piperkt.services.multimedia.application.services

import piperkt.services.multimedia.application.orThrow
import piperkt.services.multimedia.domain.session.Session
import piperkt.services.multimedia.domain.session.SessionErrors.SessionNotFound
import piperkt.services.multimedia.domain.session.SessionEvent.*
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

    fun getSession(sessionId: SessionId): Session {
        return sessionRepository.findById(sessionId).orThrow(SessionNotFound(sessionId))
    }

    fun createSession(command: Command.CreateSession): Session {
        val session = SessionFactory.fromAllowedUsers(command.allowedUsers)
        sessionRepository.save(session)
        sessionEventPublisher.publish(SessionCreated(session.id, session.allowedUsers()))
        return session
    }

    fun deleteSession(command: Command.DeleteSession) {
        sessionRepository.deleteById(command.sessionId)
    }

    private fun updateSession(id: SessionId, update: Session.() -> Unit) {
        val session = getSession(id)
        session.update()
        sessionRepository.save(session)
    }

    fun addAllowedUser(command: Command.AddAllowedUser) {
        updateSession(command.sessionId) { addAllowedUser(command.username) }
        sessionEventPublisher.publish(AllowedUserAdded(command.sessionId, command.username))
    }

    fun removeAllowedUser(command: Command.RemoveAllowedUser) {
        updateSession(command.sessionId) { removeAllowedUser(command.username) }
        sessionEventPublisher.publish(AllowedUserRemoved(command.sessionId, command.username))
    }

    fun joinSession(command: Command.JoinSession) {
        updateSession(command.sessionId) { addParticipant(command.username) }
        sessionEventPublisher.publish(ParticipantJoined(command.sessionId, command.username))
    }

    fun leaveSession(command: Command.LeaveSession) {
        updateSession(command.sessionId) { removeParticipant(command.username) }
        sessionEventPublisher.publish(ParticipantLeft(command.sessionId, command.username))
    }
}
