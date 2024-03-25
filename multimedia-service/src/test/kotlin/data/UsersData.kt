package data

import piperkt.services.multimedia.domain.User
import piperkt.services.multimedia.domain.Username

object UsersData {

    private fun named(name: String): User {
        return User(Username(name))
    }

    fun john() = named("john")

    fun jane() = named("jane")
}
