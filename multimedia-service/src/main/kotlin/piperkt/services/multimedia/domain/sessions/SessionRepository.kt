package piperkt.services.multimedia.domain.sessions

import piperkt.services.multimedia.domain.users.User

interface SessionRepository {
    fun findById(id: SessionId): Session?

    fun getSessionParticipants(sessionId: SessionId): List<User>

    fun createSession(allowedUsers: List<String>): Session

    fun addParticipant(sessionId: SessionId, username: String): Any

    fun removeParticipant(sessionId: SessionId, username: String)

    fun addAllowedUser(sessionId: SessionId, username: String)

    fun removeAllowedUser(sessionId: SessionId, username: String)

    fun deleteSession(sessionId: String)

    fun exists(sessionId: String): Boolean
}
