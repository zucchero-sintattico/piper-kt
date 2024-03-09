package piperkt.services.multimedia.infrastructure.implementation

import jakarta.inject.Singleton
import piperkt.services.multimedia.application.UserRepository
import piperkt.services.multimedia.application.UserService
import piperkt.services.multimedia.commons.events.EventPublisher

@Singleton
class UserServiceImpl(
    userRepository: UserRepository,
    eventPublisher: EventPublisher,
) : UserService(userRepository, eventPublisher)
