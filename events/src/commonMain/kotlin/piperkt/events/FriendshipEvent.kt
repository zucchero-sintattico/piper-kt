package piperkt.events

import piperkt.common.events.DomainEvent
import piperkt.common.events.EventPublisher
import kotlin.js.JsExport

@JsExport sealed interface FriendshipEvent : DomainEvent

@JsExport data class FriendshipRequestSentEvent(val fromUser: String, val toUser: String) : FriendshipEvent

@JsExport data class FriendshipRequestAcceptedEvent(val fromUser: String, val toUser: String) : FriendshipEvent

@JsExport data class FriendshipRequestRejectedEvent(val fromUser: String, val toUser: String) : FriendshipEvent

@JsExport data class NewMessageInFriendshipEvent(
    val fromUser: String,
    val toUser: String,
    val messageId: String,
) : FriendshipEvent

interface FriendshipEventPublisher : EventPublisher<FriendshipEvent>
