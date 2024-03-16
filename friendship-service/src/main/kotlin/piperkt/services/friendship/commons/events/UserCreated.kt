package piperkt.services.friendship.commons.events

data class UserCreated(
    val email: String,
    val password: String,
) : Event
