package piperkt.events

import piperkt.common.events.DomainEvent
import piperkt.common.events.EventPublisher
import kotlin.js.JsExport

@JsExport sealed interface ChannelEvent : DomainEvent

@JsExport  data class ChannelCreatedEvent(val serverId: String, val channelId: String) : ChannelEvent

@JsExport  data class ChannelUpdatedEvent(val serverId: String, val channelId: String) : ChannelEvent

@JsExport  data class ChannelDeletedEvent(val serverId: String, val channelId: String) : ChannelEvent

@JsExport  data class MessageInChannelEvent(
    val serverId: String,
    val channelId: String,
    val messageId: String,
) : ChannelEvent


interface ChannelEventPublisher : EventPublisher<ChannelEvent>
