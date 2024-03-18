package data

import piperkt.services.multimedia.domain.sessions.Session
import piperkt.services.multimedia.domain.sessions.SessionId
import piperkt.services.multimedia.domain.users.User
import piperkt.services.multimedia.domain.users.UserId

object SessionsData {

    fun simpleSession(): Session {
        val users = setOf(User(UserId("user1")), User(UserId("user2")))
        return Session.create(SessionId("randomId"), users.toList(), users.toList())
    }

    fun fromUsers(users: Set<User>): Session {
        return Session.create(SessionId("randomId"), users.toList(), users.toList())
    }
}
