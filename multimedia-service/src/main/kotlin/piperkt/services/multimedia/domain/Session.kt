package piperkt.services.multimedia.domain

data class SessionId(val value: String) {
    companion object {
        fun empty(): SessionId {
            return SessionId("")
        }
    }
}

class Session(
    val id: SessionId = SessionId.empty(),
    val allowedUsers: List<User> = emptyList(),
    val participants: List<User> = emptyList()
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Session

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}

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
