package piperkt.services.multimedia.domain.sessions

import piperkt.services.multimedia.domain.users.User

interface SessionRepository {
    fun findById(id: SessionId): Session?

    fun getUsersInSession(sessionId: SessionId): List<User>
}
