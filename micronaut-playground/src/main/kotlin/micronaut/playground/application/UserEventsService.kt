package micronaut.playground.application

import micronaut.playground.application.api.UserEventsApi
import micronaut.playground.commons.events.UserCreated
import micronaut.playground.domain.User
import micronaut.playground.domain.UserRepository

class UserEventsService(private val userRepository: UserRepository) : UserEventsApi {

    override fun onUserCreated(userCreated: UserCreated) {
        userRepository.save(User(email = userCreated.email, password = userCreated.password))
    }
}
