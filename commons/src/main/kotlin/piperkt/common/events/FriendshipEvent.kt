package piperkt.common.events

import piperkt.common.DomainEvent
import piperkt.common.EventPublisher
import piperkt.common.id.MessageId

sealed interface FriendshipEvent : DomainEvent {

    data class FriendshipRequestSentEvent(val fromUser: String, val toUser: String) :
        FriendshipEvent

    data class FriendshipRequestAcceptedEvent(val fromUser: String, val toUser: String) :
        FriendshipEvent

    data class NewMessageInFriendshipEvent(
        val fromUser: String,
        val toUser: String,
        val messageId: MessageId
    ) : FriendshipEvent
}

interface FriendshipEventPublisher : EventPublisher<FriendshipEvent>
