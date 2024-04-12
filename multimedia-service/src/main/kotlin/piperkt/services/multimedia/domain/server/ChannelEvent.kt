package piperkt.services.multimedia.domain.server

import piperkt.common.DomainEvent

sealed interface ChannelEvent : DomainEvent {
    data class ChannelCreated(
        val sessionId: String,
        val channelId: String,
        val channelType: String
    ) : ChannelEvent

    data class ChannelDeleted(val sessionId: String, val channelId: String) : ChannelEvent
}
