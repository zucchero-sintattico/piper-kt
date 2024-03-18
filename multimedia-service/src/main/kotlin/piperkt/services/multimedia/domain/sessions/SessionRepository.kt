package piperkt.services.multimedia.domain.sessions

import piperkt.services.multimedia.domain.users.User

interface SessionRepository {
    fun findById(id: SessionId): Session?

    fun getSessionParticipants(sessionId: SessionId): List<User>

    fun createSession(allowedUsers: List<String>): Session

    fun addParticipant(sessionId: SessionId, user: User): Boolean

    fun removeParticipant(sessionId: SessionId, user: User): Boolean

    fun addAllowedUser(sessionId: SessionId, user: User): Boolean

    fun removeAllowedUser(sessionId: SessionId, user: User): Boolean

    fun deleteSession(sessionId: String)

    fun exists(sessionId: String): Boolean
}
