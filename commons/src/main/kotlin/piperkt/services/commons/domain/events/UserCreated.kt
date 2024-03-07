package piperkt.services.template.commons.events

data class UserCreated(
    val email: String,
    val password: String,
) : Event
