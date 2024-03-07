package piperkt.services.commons.domain.events

data class UserCreated(
    val email: String,
    val password: String,
) : Event
