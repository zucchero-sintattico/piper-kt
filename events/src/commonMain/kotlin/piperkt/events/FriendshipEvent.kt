package piperkt.events

import piperkt.common.events.DomainEvent
import piperkt.common.events.EventPublisher
import kotlin.js.JsExport

@JsExport
sealed interface FriendshipEvent : DomainEvent

@JsExport
data class FriendshipRequestSentEvent(val fromUser: String, val toUser: String) : FriendshipEvent {
    companion object {
        const val TOPIC: String = "friendship-request-sent"
    }
}

@JsExport
data class FriendshipRequestAcceptedEvent(val fromUser: String, val toUser: String) : FriendshipEvent {
    companion object {
        const val TOPIC: String = "friendship-request-accepted"
    }
}

@JsExport
data class FriendshipRequestRejectedEvent(val fromUser: String, val toUser: String) : FriendshipEvent {
    companion object {
        const val TOPIC: String = "friendship-request-rejected"
    }
}

@JsExport
data class NewMessageInFriendshipEvent(
    val fromUser: String,
    val toUser: String,
    val messageId: String,
    val content: String
) : FriendshipEvent {
    companion object {
        const val TOPIC: String = "new-message-in-friendship"
    }
}

interface FriendshipEventPublisher : EventPublisher<FriendshipEvent>
