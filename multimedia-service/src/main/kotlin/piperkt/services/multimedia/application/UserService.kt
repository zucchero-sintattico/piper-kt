package piperkt.services.multimedia.application

import piperkt.services.multimedia.application.api.UserServiceApi
import piperkt.services.multimedia.commons.events.EventPublisher
import piperkt.services.multimedia.commons.events.UserCreated
import piperkt.services.multimedia.domain.User

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
