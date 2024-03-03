package micronaut.playground.application

import micronaut.playground.application.api.UserServiceApi
import micronaut.playground.commons.events.EventPublisher
import micronaut.playground.commons.events.UserCreated
import micronaut.playground.domain.User
import micronaut.playground.domain.UserRepository

open class UserService(
    private val userRepository: UserRepository,
    private val eventPublisher: EventPublisher,
) : UserServiceApi {

    override fun registerUser(email: String, password: String): User {
        val user = User(email = email, password = password)
        return userRepository.save(user).also {
            eventPublisher.publish(UserCreated(email = email, password = password))
        }
    }

    override fun loginUser(email: String, password: String): User? {
        return userRepository.findByEmail(email)?.takeIf { it.password == password }
    }
}
