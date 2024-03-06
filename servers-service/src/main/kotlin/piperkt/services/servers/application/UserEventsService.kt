package piperkt.services.servers.application

import piperkt.services.servers.application.api.UserEventsApi
import piperkt.services.servers.commons.events.UserCreated
import piperkt.services.servers.domain.User
import piperkt.services.servers.domain.UserRepository

class UserEventsService(private val userRepository: UserRepository) : UserEventsApi {
    override fun onUserCreated(userCreated: UserCreated) {
        userRepository.save(User(email = userCreated.email, password = userCreated.password))
    }
}
