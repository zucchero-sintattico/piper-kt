package piperkt.services.users.domain.user

import piperkt.common.DomainEvent
import piperkt.common.EventPublisher

sealed interface UserEvent : DomainEvent {
    data class UserCreated(
        val username: Username,
        val description: String,
        val profilePicture: ByteArray
    ) : UserEvent

    data class UserUpdated(
        val username: Username,
        val description: String? = null,
        val profilePicture: ByteArray? = null
    ) : UserEvent

    data class UserLoggedIn(val username: Username) : UserEvent

    data class UserLoggedOut(val username: Username) : UserEvent
}

interface UserEventPublisher : EventPublisher<UserEvent>
