package micronaut.playground.application.api

import micronaut.playground.domain.User

interface UserServiceApi {
    fun registerUser(email: String, password: String): User

    fun loginUser(email: String, password: String): User?
}
