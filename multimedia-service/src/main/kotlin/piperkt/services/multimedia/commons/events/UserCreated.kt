package piperkt.services.multimedia.commons.events

data class UserCreated(
    val email: String,
    val password: String,
) : Event
