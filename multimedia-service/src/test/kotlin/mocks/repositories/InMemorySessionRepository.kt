package mocks.repositories

import java.util.*
import piperkt.services.multimedia.domain.Session
import piperkt.services.multimedia.domain.SessionId
import piperkt.services.multimedia.domain.SessionRepository
import piperkt.services.multimedia.domain.User
import piperkt.services.multimedia.domain.Username

class InMemorySessionRepository(val sessions: MutableMap<String, Session> = mutableMapOf()) :
    SessionRepository {

    fun clear() {
        sessions.clear()
    }

    override fun findById(id: SessionId): Session? {
        return sessions[id.value]
    }

    override fun getSessionParticipants(sessionId: SessionId): List<User> {
        return sessions[sessionId.value]?.participants ?: emptyList()
    }

    private fun createUniqueID(): SessionId {
        return SessionId(UUID.randomUUID().toString())
    }

    override fun createSession(allowedUsers: List<String>): Session {
        val session = Session(createUniqueID(), allowedUsers.map { User(Username(it)) })
        sessions[session.id.value] = session
        return session
    }

    override fun addParticipant(sessionId: SessionId, user: User): Boolean {
        val session = sessions[sessionId.value] ?: return false
        if (session.participants.contains(user)) return false
        sessions[sessionId.value] =
            Session(
                session.id,
                allowedUsers = session.allowedUsers,
                participants = session.participants + user
            )
        return true
    }

    override fun removeParticipant(sessionId: SessionId, user: User): Boolean {
        val session = sessions[sessionId.value] ?: return false
        if (!session.participants.contains(user)) return false
        sessions[sessionId.value] =
            Session(session.id, session.allowedUsers, session.participants - user)
        return true
    }

    override fun addAllowedUser(sessionId: SessionId, user: User): Boolean {
        val session = sessions[sessionId.value] ?: return false
        if (session.allowedUsers.contains(user)) return false
        sessions[sessionId.value] = Session(session.id, session.allowedUsers + user)
        return true
    }

    override fun removeAllowedUser(sessionId: SessionId, user: User): Boolean {
        val session = sessions[sessionId.value] ?: return false
        if (!session.allowedUsers.contains(user)) return false
        sessions[sessionId.value] = Session(session.id, session.allowedUsers - user)
        return true
    }

    override fun deleteSession(sessionId: String) {
        sessions.remove(sessionId)
    }

    override fun exists(sessionId: String): Boolean {
        return sessions.containsKey(sessionId)
    }
}
