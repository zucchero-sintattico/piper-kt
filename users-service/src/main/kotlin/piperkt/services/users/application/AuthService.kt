package piperkt.services.users.application

import org.mindrot.jbcrypt.BCrypt.*
import piperkt.common.orThrow
import piperkt.services.users.domain.user.User
import piperkt.services.users.domain.user.UserError
import piperkt.services.users.domain.user.UserEvent
import piperkt.services.users.domain.user.UserEventPublisher
import piperkt.services.users.domain.user.UserFactory
import piperkt.services.users.domain.user.UserRepository
import piperkt.services.users.domain.user.Username

open class AuthService(
    private val userRepository: UserRepository,
    private val userEventPublisher: UserEventPublisher
) {

    private fun getUserOrThrow(username: String, error: (Username) -> UserError): User {
        return userRepository.findByUsername(username).orThrow(error(Username(username)))
    }

    private fun updateUser(username: Username, update: User.() -> Unit): User {
        val user =
            userRepository.findByUsername(username.value).orThrow(UserError.UserNotFound(username))
        user.update()
        userRepository.save(user)
        return user
    }

    fun register(
        username: String,
        password: String,
        description: String? = null,
        photo: String? = null
    ): User {
        if (userRepository.findByUsername(username) != null) {
            throw UserError.UserAlreadyExists(Username(username))
        }
        val salt = gensalt()
        val hashedPassword = hashpw(password, salt)
        val user = UserFactory.create(Username(username), hashedPassword, description, photo)
        userRepository.save(user)
        userEventPublisher.publish(
            UserEvent.UserCreated(user.username.value, user.description, user.profilePicture)
        )
        return user
    }

    fun login(username: String, password: String): User {
        val user = getUserOrThrow(username) { UserError.UserNotFound(it) }
        if (!checkpw(password, user.password)) {
            throw UserError.InvalidPassword(password)
        }
        return user
    }

    fun saveRefreshToken(username: String, refreshToken: String): User {
        updateUser(Username(username)) { updateRefreshToken(refreshToken) }
        return getUserOrThrow(username) { UserError.UserNotFound(it) }
    }

    fun getUserByRefreshToken(refreshToken: String): User {
        return userRepository
            .findByRefreshToken(refreshToken)
            .orThrow(UserError.RefreshTokenNotFound(refreshToken))
    }

    fun delete(username: String) {
        userRepository.deleteById(Username(username))
    }
}
