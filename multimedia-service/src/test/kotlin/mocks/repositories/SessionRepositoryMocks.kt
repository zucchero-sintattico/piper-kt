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

    companion object {
        fun fromSessions(vararg sessions: Session): SessionRepositoryMocks {
            return SessionRepositoryMocks(sessions.associateBy { it.id }.toMutableMap())
        }
    }
}
