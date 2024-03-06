package piperkt.services.template.application.api

import piperkt.services.template.domain.User

interface UserServiceApi {
  fun registerUser(
      email: String,
      password: String,
  ): User

  fun loginUser(
      email: String,
      password: String,
  ): User?
}
