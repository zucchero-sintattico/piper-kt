package piperkt.services.commons.domain.events

import piperkt.services.commons.domain.id.ServerId

sealed interface ServerEvent : DomainEvent {
    data class ServerCreatedEvent(val serverId: ServerId) : DomainEvent

    data class ServerUpdatedEvent(val serverId: ServerId) : DomainEvent

    data class ServerUserAddedEvent(val serverId: ServerId, val username: String) : DomainEvent

    data class ServerUserRemovedEvent(val serverId: ServerId, val username: String) : DomainEvent

    data class ServerDeletedEvent(val serverId: ServerId) : DomainEvent
}
