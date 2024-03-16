package piperkt.services.friendship.infrastructure.implementation

import jakarta.inject.Singleton
import piperkt.services.friendship.application.UserService
import piperkt.services.friendship.commons.events.EventPublisher
import piperkt.services.friendship.domain.UserRepository

@Singleton
class UserServiceImpl(
    userRepository: UserRepository,
    eventPublisher: EventPublisher,
) : UserService(userRepository, eventPublisher)
