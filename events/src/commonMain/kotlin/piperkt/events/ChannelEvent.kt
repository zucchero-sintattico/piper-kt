package piperkt.events

import piperkt.common.events.DomainEvent
import piperkt.common.events.EventPublisher
import kotlin.js.JsExport

@JsExport
sealed interface ChannelEvent : DomainEvent

@JsExport
data class ChannelCreatedEvent(val serverId: String, val channelId: String) : ChannelEvent {
    companion object {
        const val TOPIC = "channel-created"
    }
}

@JsExport
data class ChannelUpdatedEvent(val serverId: String, val channelId: String) : ChannelEvent {
    companion object {
        const val TOPIC = "channel-updated"
    }
}

@JsExport
data class ChannelDeletedEvent(val serverId: String, val channelId: String) : ChannelEvent {
    companion object {
        const val TOPIC = "channel-deleted"
    }
}

@JsExport
data class MessageInChannelEvent(
    val serverId: String,
    val channelId: String,
    val messageId: String,
    val sender: String,
    val content: String
) : ChannelEvent {
    companion object {
        const val TOPIC = "message-in-channel"
    }
}


interface ChannelEventPublisher : EventPublisher<ChannelEvent>
