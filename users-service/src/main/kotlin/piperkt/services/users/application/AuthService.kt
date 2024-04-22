package piperkt.services.users.application

import org.mindrot.jbcrypt.BCrypt
import piperkt.services.users.domain.user.User
import piperkt.services.users.domain.user.UserError
import piperkt.services.users.domain.user.UserRepository
import piperkt.services.users.domain.user.Username

data class RegisterRequest(
    val username: String,
    val password: String,
    val description: String = "",
    val photo: ByteArray = byteArrayOf()
)

open class AuthService(private val userRepository: UserRepository) {

    private fun getUserOrThrow(username: String, error: (Username) -> UserError): User {
        return userRepository.findByUsername(username) ?: throw error(Username(username))
    }

    fun register(request: RegisterRequest): User {
        if (userRepository.findByUsername(request.username) != null) {
            throw UserError.UserAlreadyExists(Username(request.username))
        }
        val salt = BCrypt.gensalt()
        val hashedPassword = BCrypt.hashpw(request.password, salt)
        val user =
            User(Username(request.username), hashedPassword, request.description, request.photo)
        userRepository.save(user)
        return user
    }

    fun login(username: String, password: String): User {
        val user = getUserOrThrow(username) { UserError.UserNotFound(it) }
        if (!BCrypt.checkpw(password, user.password)) {
            throw UserError.InvalidPassword(password)
        }
        return user
    }

    fun saveRefreshToken(username: String, refreshToken: String) {
        val user = getUserOrThrow(username) { UserError.UserNotFound(it) }
        user.updateRefreshToken(refreshToken)
        userRepository.save(user)
    }

    fun findUserByRefreshToken(refreshToken: String): User {
        return userRepository.findByRefreshToken(refreshToken)
            ?: throw UserError.RefreshTokenNotFound(refreshToken)
    }

    fun logout(username: String) {
        val user = getUserOrThrow(username) { UserError.UserNotFound(it) }
        user.clearRefreshToken()
        userRepository.save(user)
    }
}
