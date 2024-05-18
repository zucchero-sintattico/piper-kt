package piperkt.events

import piperkt.common.events.DomainEvent
import piperkt.common.events.EventPublisher

sealed interface FriendshipEvent : DomainEvent {

    data class FriendshipRequestSentEvent(val fromUser: String, val toUser: String) :
        FriendshipEvent

    data class FriendshipRequestAcceptedEvent(val fromUser: String, val toUser: String) :
        FriendshipEvent

    data class FriendshipRequestRejectedEvent(val fromUser: String, val toUser: String) :
        FriendshipEvent

    class NewMessageInFriendshipEvent(
        val fromUser: String,
        val toUser: String,
        val messageId: String,
    ) : FriendshipEvent
}

interface FriendshipEventPublisher : EventPublisher<FriendshipEvent>
