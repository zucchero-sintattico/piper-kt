package piperkt.services.multimedia.domain.sessions

import piperkt.services.multimedia.domain.users.User

interface SessionRepository {
    fun getUsersInSession(sessionId: SessionId): List<User>
}
