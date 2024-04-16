package piperkt.services.multimedia.infrastructure.implementation

import jakarta.inject.Singleton
import piperkt.services.users.application.AuthService
import piperkt.services.users.domain.user.UserRepository

object Services {
    @Singleton class AuthServiceImpl(userRepository: UserRepository) : AuthService(userRepository)
}
