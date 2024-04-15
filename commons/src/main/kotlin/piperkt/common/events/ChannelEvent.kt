package piperkt.common.events

import piperkt.common.DomainEvent
import piperkt.common.EventPublisher
import piperkt.common.id.ChannelId
import piperkt.common.id.MessageId

sealed interface ChannelEvent : DomainEvent {
    data class ChannelCreatedEvent(val channelId: ChannelId) : ChannelEvent

    data class ChannelUpdatedEvent(val channelId: ChannelId) : ChannelEvent

    data class ChannelDeletedEvent(val channelId: ChannelId) : ChannelEvent

    data class MessageInChannelEvent(val channelId: ChannelId, val messageId: MessageId) :
        ChannelEvent
}

interface ChannelEventPublisher : EventPublisher<ChannelEvent>
