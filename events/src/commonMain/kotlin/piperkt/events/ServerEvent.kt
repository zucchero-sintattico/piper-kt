package piperkt.events

import piperkt.common.events.DomainEvent
import piperkt.common.events.EventPublisher

sealed interface ServerEvent : DomainEvent {
    data class ServerCreatedEvent(val serverId: String, val owner: String) : ServerEvent

    data class ServerUpdatedEvent(val serverId: String) : ServerEvent

    data class ServerUserAddedEvent(val serverId: String, val username: String) : ServerEvent

    data class ServerUserRemovedEvent(val serverId: String, val username: String) : ServerEvent

    data class ServerUserKickedEvent(val serverId: String, val username: String) : ServerEvent

    data class ServerDeletedEvent(val serverId: String) : ServerEvent
}

interface ServerEventPublisher : EventPublisher<ServerEvent>
