package piperkt.services.multimedia.domain.events

sealed interface UserEvent : DomainEvent {

    data class UserJoinedServer(val username: String, val serverId: String) : UserEvent

    data class UserLeftServer(val username: String, val serverId: String) : UserEvent

    data class UserKickedFromServer(val username: String, val serverId: String) : UserEvent
}
