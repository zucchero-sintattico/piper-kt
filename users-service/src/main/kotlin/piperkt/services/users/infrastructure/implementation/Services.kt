package piperkt.services.users.infrastructure.implementation

import jakarta.inject.Singleton
import piperkt.services.users.application.AuthService
import piperkt.services.users.application.UserService
import piperkt.services.users.domain.user.UserEventPublisher
import piperkt.services.users.domain.user.UserRepository

object Services {
    @Singleton
    class UserServiceImpl(userRepository: UserRepository, userEventPublisher: UserEventPublisher) :
        UserService(userRepository, userEventPublisher)

    @Singleton
    class AuthServiceImpl(userRepository: UserRepository, userEventPublisher: UserEventPublisher) :
        AuthService(userRepository, userEventPublisher)
}
