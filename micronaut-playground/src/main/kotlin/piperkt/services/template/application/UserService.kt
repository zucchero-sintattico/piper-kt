package piperkt.services.template.application

import piperkt.services.template.application.api.UserServiceApi
import piperkt.services.template.commons.events.EventPublisher
import piperkt.services.template.commons.events.UserCreated
import piperkt.services.template.domain.User
import piperkt.services.template.domain.UserRepository

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
