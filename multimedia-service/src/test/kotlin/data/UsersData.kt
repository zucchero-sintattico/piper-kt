package data

import piperkt.services.multimedia.domain.user.User
import piperkt.services.multimedia.domain.user.UserId

object UsersData {

    private fun named(name: String): User {
        return User(UserId(name))
    }

    fun john() = named("john")

    fun jane() = named("jane")
}
