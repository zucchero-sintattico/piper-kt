package piperkt.events

import piperkt.common.events.DomainEvent
import piperkt.common.events.EventPublisher
import kotlin.js.JsExport

@JsExport sealed interface ServerEvent : DomainEvent

@JsExport data class ServerCreatedEvent(val serverId: String, val owner: String) : ServerEvent

@JsExport data class ServerUpdatedEvent(val serverId: String) : ServerEvent

@JsExport data class ServerUserAddedEvent(val serverId: String, val username: String) : ServerEvent

@JsExport data class ServerUserRemovedEvent(val serverId: String, val username: String) : ServerEvent

@JsExport data class ServerUserKickedEvent(val serverId: String, val username: String) : ServerEvent

@JsExport data class ServerDeletedEvent(val serverId: String) : ServerEvent

interface ServerEventPublisher : EventPublisher<ServerEvent>
