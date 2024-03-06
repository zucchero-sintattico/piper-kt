package piperkt.services.servers.application.api

import piperkt.services.servers.domain.User

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
