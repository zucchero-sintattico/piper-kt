package piperkt.services.multimedia.domain.server

import piperkt.common.DomainEvent

sealed interface ServerEvent : DomainEvent {
    data class ServerCreated(val serverId: String, val owner: String) : ServerEvent

    data class ServerDeleted(val serverId: String) : ServerEvent

    data class UserJoinedServer(val username: String, val serverId: String) : ServerEvent

    data class UserLeftServer(val username: String, val serverId: String) : ServerEvent

    data class UserKickedFromServer(val username: String, val serverId: String) : ServerEvent
}
