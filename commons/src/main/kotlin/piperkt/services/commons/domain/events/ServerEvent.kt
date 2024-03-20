package piperkt.services.commons.domain.events

import piperkt.services.commons.domain.id.ServerId

sealed interface ServerEvent : DomainEvent {
    data class ServerCreatedEvent(val serverId: ServerId) : ServerEvent

    data class ServerUpdatedEvent(val serverId: ServerId) : ServerEvent

    data class ServerUserAddedEvent(val serverId: ServerId, val username: String) : ServerEvent

    data class ServerUserRemovedEvent(val serverId: ServerId, val username: String) : ServerEvent

    data class ServerUserKickedEvent(val serverId: ServerId, val username: String) : ServerEvent

    data class ServerDeletedEvent(val serverId: ServerId) : ServerEvent
}

interface ServerEventPublisher : DomainEventPublisher<ServerEvent>
