package piperkt.services.users.application

import org.mindrot.jbcrypt.BCrypt
import piperkt.services.users.domain.user.User
import piperkt.services.users.domain.user.UserError
import piperkt.services.users.domain.user.UserRepository
import piperkt.services.users.domain.user.Username

open class AuthService(private val userRepository: UserRepository) {

    fun register(username: String, password: String): User {
        val salt = BCrypt.gensalt()
        val hashedPassword = BCrypt.hashpw(password, salt)
        val user = User(Username(username), hashedPassword)
        userRepository.save(user)
        return user
    }

    fun login(username: String, password: String): User {
        val user =
            userRepository.findByUsername(username)
                ?: throw UserError.UserNotFound(Username(username))
        if (!BCrypt.checkpw(password, user.password)) {
            throw UserError.InvalidPassword(password)
        }
        return user
    }
}
