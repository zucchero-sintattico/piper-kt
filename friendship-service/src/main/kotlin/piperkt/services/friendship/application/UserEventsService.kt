package piperkt.services.friendship.application

import piperkt.services.friendship.application.api.UserEventsApi
import piperkt.services.friendship.commons.events.UserCreated
import piperkt.services.friendship.domain.User
import piperkt.services.friendship.domain.UserRepository

class UserEventsService(private val userRepository: UserRepository) : UserEventsApi {
    override fun onUserCreated(userCreated: UserCreated) {
        userRepository.save(User(email = userCreated.email, password = userCreated.password))
    }
}
