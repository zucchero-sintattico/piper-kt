package piperkt.services.commons.domain.events

import piperkt.services.commons.domain.id.ChannelId
import piperkt.services.commons.domain.id.MessageId

sealed interface ChannelEvent {
    data class ChannelCreatedEvent(val channelId: ChannelId) : DomainEvent

    data class ChannelUpdatedEvent(val channelId: ChannelId) : DomainEvent

    data class ChannelDeletedEvent(val channelId: ChannelId) : DomainEvent

    data class MessageInChannelEvent(val channelId: ChannelId, val messageId: MessageId) :
        DomainEvent
}
