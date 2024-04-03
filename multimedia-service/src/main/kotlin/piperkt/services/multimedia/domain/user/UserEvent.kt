package piperkt.services.multimedia.domain.user

import piperkt.services.multimedia.common.DomainEvent

sealed interface UserEvent : DomainEvent {

    data class UserJoinedServer(val username: String, val serverId: String) : UserEvent

    data class UserLeftServer(val username: String, val serverId: String) : UserEvent

    data class UserKickedFromServer(val username: String, val serverId: String) : UserEvent
}
