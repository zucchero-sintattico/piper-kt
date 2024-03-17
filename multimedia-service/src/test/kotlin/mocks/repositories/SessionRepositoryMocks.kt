package mocks.repositories

import piperkt.services.multimedia.domain.sessions.Session
import piperkt.services.multimedia.domain.sessions.SessionId
import piperkt.services.multimedia.domain.sessions.SessionRepository
import piperkt.services.multimedia.domain.users.User

class SessionRepositoryMocks(
    private val sessions: MutableMap<SessionId, Session> = mutableMapOf()
) : SessionRepository {

    override fun findById(id: SessionId): Session? {
        return sessions[id]
    }

    override fun getUsersInSession(sessionId: SessionId): List<User> {
        return sessions[sessionId]?.participants ?: emptyList()
    }

    override fun createSession(allowedUsers: List<String>): Session {
        TODO("Not yet implemented")
    }

    override fun addParticipant(sessionId: SessionId, username: String): Any {
        TODO("Not yet implemented")
    }

    override fun removeParticipant(sessionId: SessionId, username: String) {
        TODO("Not yet implemented")
    }

    override fun addAllowedUser(sessionId: SessionId, username: String) {
        TODO("Not yet implemented")
    }

    override fun removeAllowedUser(sessionId: SessionId, username: String) {
        TODO("Not yet implemented")
    }

    companion object {
        fun fromSessions(vararg sessions: Session): SessionRepositoryMocks {
            return SessionRepositoryMocks(sessions.associateBy { it.id }.toMutableMap())
        }
    }
}
