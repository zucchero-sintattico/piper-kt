package piperkt.services.users.application

import piperkt.common.orThrow
import piperkt.services.users.domain.user.User
import piperkt.services.users.domain.user.UserError
import piperkt.services.users.domain.user.UserRepository
import piperkt.services.users.domain.user.Username

open class UserService(private val userRepository: UserRepository) {
    fun getUser(username: String): User =
        userRepository.findByUsername(username).orThrow(UserError.UserNotFound(Username(username)))

    private fun updateUser(username: Username, update: User.() -> User): User {
        val user = getUser(username.value)
        user.update()
        userRepository.save(user)
        return user
    }
}
