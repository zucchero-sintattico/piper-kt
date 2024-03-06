package piperkt.services.multimedia.application

import piperkt.services.multimedia.application.api.UserEventsApi
import piperkt.services.multimedia.commons.events.UserCreated
import piperkt.services.multimedia.domain.User
import piperkt.services.multimedia.domain.UserRepository

class UserEventsService(private val userRepository: UserRepository) : UserEventsApi {
    override fun onUserCreated(userCreated: UserCreated) {
        userRepository.save(User(email = userCreated.email, password = userCreated.password))
    }
}
