package piperkt.events

import piperkt.common.events.DomainEvent
import piperkt.common.events.EventPublisher

sealed interface ChannelEvent : DomainEvent {
    data class ChannelCreatedEvent(val serverId: String, val channelId: String) : ChannelEvent

    data class ChannelUpdatedEvent(val serverId: String, val channelId: String) : ChannelEvent

    data class ChannelDeletedEvent(val serverId: String, val channelId: String) : ChannelEvent

    data class MessageInChannelEvent(
        val serverId: String,
        val channelId: String,
        val messageId: String,
    ) : ChannelEvent
}

interface ChannelEventPublisher : EventPublisher<ChannelEvent>