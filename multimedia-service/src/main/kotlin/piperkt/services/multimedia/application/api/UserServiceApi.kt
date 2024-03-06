package piperkt.services.multimedia.application.api

import piperkt.services.multimedia.domain.User

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
