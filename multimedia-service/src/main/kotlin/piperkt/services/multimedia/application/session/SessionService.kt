package piperkt.services.multimedia.application.session

import piperkt.common.utils.orThrow
import piperkt.events.AllowedUserAddedEvent
import piperkt.events.AllowedUserRemovedEvent
import piperkt.events.ParticipantJoinedEvent
import piperkt.events.ParticipantLeftEvent
import piperkt.events.SessionCreatedEvent
import piperkt.events.SessionEventPublisher
import piperkt.services.multimedia.domain.direct.DirectErrors
import piperkt.services.multimedia.domain.direct.DirectRepository
import piperkt.services.multimedia.domain.server.ChannelId
import piperkt.services.multimedia.domain.server.ServerErrors
import piperkt.services.multimedia.domain.server.ServerId
import piperkt.services.multimedia.domain.server.ServerRepository
import piperkt.services.multimedia.domain.session.Session
import piperkt.services.multimedia.domain.session.SessionErrors
import piperkt.services.multimedia.domain.session.SessionErrors.SessionNotFound
import piperkt.services.multimedia.domain.session.SessionFactory
import piperkt.services.multimedia.domain.session.SessionId
import piperkt.services.multimedia.domain.session.SessionRepository
import piperkt.services.multimedia.domain.user.Username

/** Service for managing sessions. */
open class SessionService(
    private val sessionRepository: SessionRepository,
    private val serverRepository: ServerRepository,
    private val directRepository: DirectRepository,
    private val sessionEventPublisher: SessionEventPublisher,
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
        sessionEventPublisher.publish(
            SessionCreatedEvent(session.id.value, session.allowedUsers().map { it.value }.toSet())
        )
        return session
    }

    fun deleteSession(command: Command.DeleteSession) {
        sessionRepository.deleteById(command.sessionId).orThrow(SessionNotFound(command.sessionId))
    }

    private fun updateSession(id: SessionId, update: Session.() -> Unit) {
        val session = getSession(id)
        session.update()
        sessionRepository.update(session)
    }

    fun addAllowedUser(command: Command.AddAllowedUser) {
        updateSession(command.sessionId) { addAllowedUser(command.username) }
        sessionEventPublisher.publish(
            AllowedUserAddedEvent(command.sessionId.value, command.username.value)
        )
    }

    fun removeAllowedUser(command: Command.RemoveAllowedUser) {
        updateSession(command.sessionId) { removeAllowedUser(command.username) }
        sessionEventPublisher.publish(
            AllowedUserRemovedEvent(command.sessionId.value, command.username.value)
        )
    }

    fun joinSession(command: Command.JoinSession) {
        updateSession(command.sessionId) { addParticipant(command.username) }
        sessionEventPublisher.publish(
            ParticipantJoinedEvent(command.sessionId.value, command.username.value)
        )
    }

    fun leaveSession(command: Command.LeaveSession) {
        updateSession(command.sessionId) { removeParticipant(command.username) }
        sessionEventPublisher.publish(
            ParticipantLeftEvent(command.sessionId.value, command.username.value)
        )
    }

    fun getChannelSessionId(author: Username, serverId: ServerId, channelId: ChannelId): SessionId {
        val server =
            serverRepository.findById(serverId).orThrow(ServerErrors.ServerNotFound(serverId))
        println("Server: ${server.id} - ${server.members()}")
        if (!server.members().contains(author)) {
            throw ServerErrors.UserNotInServer(serverId, author)
        }
        val channel = server.getChannelById(channelId)
        return channel.sessionId
    }

    fun getDirectSessionId(author: Username, target: Username): SessionId {
        val direct =
            directRepository
                .findByUsers(setOf(author, target))
                .orThrow(DirectErrors.DirectNotFound(setOf(author, target)))
        return direct.sessionId
    }

    fun getSessionParticipants(author: Username, sessionId: SessionId): Set<Username> {
        println("Getting session participants for ${sessionId.value}")
        val session = getSession(sessionId)
        if (!session.allowedUsers().contains(author)) {
            throw SessionErrors.UserNotAllowed(sessionId, author)
        }
        return session.participants()
    }
}
