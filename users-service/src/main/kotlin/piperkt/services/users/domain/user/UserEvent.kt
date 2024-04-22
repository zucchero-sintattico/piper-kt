package piperkt.services.users.domain.user

import piperkt.common.DomainEvent
import piperkt.common.EventPublisher

sealed interface UserEvent : DomainEvent {
    data class UserCreated(val user: User) : UserEvent

    data class UserUpdated(val user: User) : UserEvent

    data class UserLoggedIn(val username: Username) : UserEvent

    data class UserLoggedOut(val username: Username) : UserEvent
}

interface UserEventPublisher : EventPublisher<UserEvent>
