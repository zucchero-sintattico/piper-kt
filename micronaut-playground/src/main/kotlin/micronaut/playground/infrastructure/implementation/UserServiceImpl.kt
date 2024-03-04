package micronaut.playground.infrastructure.implementation

import jakarta.inject.Singleton
import micronaut.playground.application.UserService
import micronaut.playground.commons.events.EventPublisher
import micronaut.playground.domain.UserRepository

@Singleton
class UserServiceImpl(
    userRepository: UserRepository,
    eventPublisher: EventPublisher,
) : UserService(userRepository, eventPublisher)
