package piperkt.services.users.domain.user

import piperkt.common.DomainEvent
import piperkt.common.EventPublisher
import piperkt.services.users.domain.friends.FriendRequest

sealed interface UserEvent : DomainEvent {
    data class UserCreated(val user: User) : UserEvent

    data class UserUpdated(val user: User) : UserEvent

    data class UserLoggedIn(val username: Username) : UserEvent

    data class UserLoggedOut(val username: Username) : UserEvent

    data class FriendRequestSent(val friendRequest: FriendRequest) : UserEvent

    data class FriendRequestAccepted(val friendRequest: FriendRequest) : UserEvent

    data class FriendRequestRejected(val friendRequest: FriendRequest) : UserEvent
}

interface UserEventPublisher : EventPublisher<UserEvent>
