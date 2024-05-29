package piperkt.events

import piperkt.common.events.DomainEvent
import piperkt.common.events.EventPublisher
import kotlin.js.JsExport

@JsExport
sealed interface FriendshipEvent : DomainEvent

/**
 * Friendship request sent event.
 *
 * @param fromUser The user that sent the request.
 * @param toUser The user that received the request.
 */
@JsExport
data class FriendshipRequestSentEvent(val fromUser: String, val toUser: String) : FriendshipEvent {
    companion object {
        const val TOPIC: String = "friendship-request-sent"
    }
}

/**
 * Friendship request accepted event.
 *
 * @param fromUser The user that sent the request.
 * @param toUser The user that received the request.
 */
@JsExport
data class FriendshipRequestAcceptedEvent(val fromUser: String, val toUser: String) : FriendshipEvent {
    companion object {
        const val TOPIC: String = "friendship-request-accepted"
    }
}

/**
 * Friendship request rejected event.
 *
 * @param fromUser The user that sent the request.
 * @param toUser The user that received the request.
 */
@JsExport
data class FriendshipRequestRejectedEvent(val fromUser: String, val toUser: String) : FriendshipEvent {
    companion object {
        const val TOPIC: String = "friendship-request-rejected"
    }
}

/**
 * New message in friendship event.
 *
 * @param fromUser The user that sent the message.
 * @param toUser The user that received the message.
 * @param messageId The id of the message.
 * @param content The content of the message.
 */
@JsExport
data class NewMessageInFriendshipEvent(
    val fromUser: String,
    val toUser: String,
    val messageId: String,
    val content: String,
) : FriendshipEvent {
    companion object {
        const val TOPIC: String = "new-message-in-friendship"
    }
}

interface FriendshipEventPublisher : EventPublisher<FriendshipEvent>
