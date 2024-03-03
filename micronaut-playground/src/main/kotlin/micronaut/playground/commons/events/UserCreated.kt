package micronaut.playground.commons.events

data class UserCreated(
    val email: String,
    val password: String,
) : Event
