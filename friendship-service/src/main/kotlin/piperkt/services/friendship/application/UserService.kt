package piperkt.services.friendship.application

import piperkt.services.friendship.application.api.UserServiceApi
import piperkt.services.friendship.commons.events.EventPublisher
import piperkt.services.friendship.commons.events.UserCreated
import piperkt.services.friendship.domain.User
import piperkt.services.friendship.domain.UserRepository

open class UserService(
    private val userRepository: UserRepository,
    private val eventPublisher: EventPublisher,
) : UserServiceApi {
    override fun registerUser(
        email: String,
        password: String,
    ): User {
        val user = User(email = email, password = password)
        return userRepository.save(user).also {
            eventPublisher.publish(UserCreated(email = email, password = password))
        }
    }

    override fun loginUser(
        email: String,
        password: String,
    ): User? = userRepository.findByEmail(email)?.takeIf { it.password == password }
}
