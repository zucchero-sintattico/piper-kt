package piperkt.services.multimedia.domain.events

sealed interface ServerEvent : DomainEvent {
    data class ServerCreated(val serverId: String, val owner: String) : ServerEvent

    data class ServerDeleted(val serverId: String) : ServerEvent
}
