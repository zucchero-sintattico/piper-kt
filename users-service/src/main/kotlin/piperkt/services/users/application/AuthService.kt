package piperkt.services.users.application

import org.mindrot.jbcrypt.BCrypt.*
import piperkt.common.utils.orThrow
import piperkt.events.UserCreatedEvent
import piperkt.events.UserEventPublisher
import piperkt.services.users.domain.user.User
import piperkt.services.users.domain.user.UserError
import piperkt.services.users.domain.user.UserFactory
import piperkt.services.users.domain.user.UserRepository
import piperkt.services.users.domain.user.Username

open class AuthService(
    private val userRepository: UserRepository,
    private val userEventPublisher: UserEventPublisher,
) {

    private fun getUserOrThrow(username: String, error: (Username) -> UserError): User {
        return userRepository.findByUsername(username).orThrow(error(Username(username)))
    }

    private fun updateUser(username: Username, update: User.() -> Unit): User {
        val user =
            userRepository.findByUsername(username.value).orThrow(UserError.UserNotFound(username))
        user.update()
        userRepository.update(user)
        return user
    }

    fun register(
        username: String,
        password: String,
        email: String? = null,
        description: String? = null,
        photo: String? = null,
    ): User {
        if (userRepository.findByUsername(username) != null) {
            throw UserError.UserAlreadyExists(Username(username))
        }
        val salt = gensalt()
        val hashedPassword = hashpw(password, salt)
        val user = UserFactory.create(Username(username), hashedPassword, email, description, photo)
        userRepository.save(user)
        userEventPublisher.publish(
            UserCreatedEvent(user.username.value, user.email, user.description, user.profilePicture)
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
