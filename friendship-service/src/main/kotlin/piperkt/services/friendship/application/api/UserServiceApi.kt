package piperkt.services.friendship.application.api

import piperkt.services.friendship.domain.User

interface UserServiceApi {
    fun registerUser(
        email: String,
        password: String,
    ): User

    fun loginUser(
        email: String,
        password: String,
    ): User?
}
