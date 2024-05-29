package piperkt.events

import piperkt.common.events.DomainEvent
import piperkt.common.events.EventPublisher
import kotlin.js.JsExport

@JsExport
sealed interface ChannelEvent : DomainEvent

/**
 * Channel created event.
 *
 * @param serverId The id of the server.
 * @param channelId The id of the channel.
 */
@JsExport
data class ChannelCreatedEvent(val serverId: String, val channelId: String) : ChannelEvent {
    companion object {
        const val TOPIC = "channel-created"
    }
}

/**
 * Channel updated event.
 *
 * @param serverId The id of the server.
 * @param channelId The id of the channel.
 */
@JsExport
data class ChannelUpdatedEvent(val serverId: String, val channelId: String) : ChannelEvent {
    companion object {
        const val TOPIC = "channel-updated"
    }
}

/**
 * Channel deleted event.
 *
 * @param serverId The id of the server.
 * @param channelId The id of the channel.
 */
@JsExport
data class ChannelDeletedEvent(val serverId: String, val channelId: String) : ChannelEvent {
    companion object {
        const val TOPIC = "channel-deleted"
    }
}

/**
 * Message in channel event.
 *
 * @param serverId The id of the server.
 * @param channelId The id of the channel.
 * @param messageId The id of the message.
 * @param sender The sender of the message.
 * @param content The content of the message.
 */
@JsExport
data class MessageInChannelEvent(
    val serverId: String,
    val channelId: String,
    val messageId: String,
    val sender: String,
    val content: String,
) : ChannelEvent {
    companion object {
        const val TOPIC = "message-in-channel"
    }
}


interface ChannelEventPublisher : EventPublisher<ChannelEvent>
