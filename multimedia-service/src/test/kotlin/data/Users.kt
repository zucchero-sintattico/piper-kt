package data

import piperkt.services.multimedia.domain.users.User
import piperkt.services.multimedia.domain.users.Username

object Users {

    private fun named(name: String): User {
        return User(Username(name))
    }

    fun john() = named("john")

    fun jane() = named("jane")
}
