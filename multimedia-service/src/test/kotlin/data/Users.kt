package data

import piperkt.services.multimedia.domain.users.User
import piperkt.services.multimedia.domain.users.UserId

object Users {

    private fun named(name: String): User {
        return User(UserId(name))
    }

    fun john() = named("john")

    fun jane() = named("jane")
}
