package piperkt.services.servers.commons.events

data class UserCreated(
    val email: String,
    val password: String,
) : Event
