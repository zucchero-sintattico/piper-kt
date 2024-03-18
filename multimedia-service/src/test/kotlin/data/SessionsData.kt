package data

import piperkt.services.multimedia.domain.sessions.Session
import piperkt.services.multimedia.domain.sessions.SessionId
import piperkt.services.multimedia.domain.users.User
import piperkt.services.multimedia.domain.users.Username

object SessionsData {

    fun empty() = Session()

    fun simpleSession(): Session {
        val users = setOf(User(Username("user1")), User(Username("user2")))
        return Session(SessionId("randomId"), users.toList(), users.toList())
    }

    fun fromUsers(users: Set<User>): Session {
        return Session(SessionId("randomId"), users.toList(), users.toList())
    }
}
