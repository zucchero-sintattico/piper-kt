package piperkt.services.template.infrastructure.implementation

import jakarta.inject.Singleton
import piperkt.services.template.commons.events.EventPublisher
import piperkt.services.template.domain.UserRepository

@Singleton
class UserServiceImpl(
    userRepository: UserRepository,
    eventPublisher: EventPublisher,
) : piperkt.services.template.application.UserService(userRepository, eventPublisher)
