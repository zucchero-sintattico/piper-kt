package piperkt.services.template.application

import piperkt.services.template.application.api.UserEventsApi
import piperkt.services.template.commons.events.UserCreated
import piperkt.services.template.domain.User
import piperkt.services.template.domain.UserRepository

class UserEventsService(private val userRepository: UserRepository) : UserEventsApi {
  override fun onUserCreated(userCreated: UserCreated) {
    userRepository.save(User(email = userCreated.email, password = userCreated.password))
  }
}
