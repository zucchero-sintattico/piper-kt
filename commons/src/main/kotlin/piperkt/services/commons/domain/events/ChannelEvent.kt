package piperkt.services.commons.domain.events

import piperkt.services.commons.domain.id.ChannelId
import piperkt.services.commons.domain.id.MessageId

sealed interface ChannelEvent : DomainEvent {
    data class ChannelCreatedEvent(val channelId: ChannelId) : ChannelEvent

    data class ChannelUpdatedEvent(val channelId: ChannelId) : ChannelEvent

    data class ChannelDeletedEvent(val channelId: ChannelId) : ChannelEvent

    data class MessageInChannelEvent(val channelId: ChannelId, val messageId: MessageId) :
        ChannelEvent
}

interface ChannelEventPublisher : DomainEventPublisher<ChannelEvent>
