package piperkt.services.servers.infrastructure.implementation

import jakarta.inject.Singleton
import piperkt.services.servers.application.UserService
import piperkt.services.servers.commons.events.EventPublisher
import piperkt.services.servers.domain.UserRepository

@Singleton
class UserServiceImpl(
    userRepository: UserRepository,
    eventPublisher: EventPublisher,
) : UserService(userRepository, eventPublisher)
