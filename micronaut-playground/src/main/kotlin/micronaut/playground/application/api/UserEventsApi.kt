package micronaut.playground.application.api

import micronaut.playground.commons.events.UserCreated

interface UserEventsApi {
    fun onUserCreated(userCreated: UserCreated)
}
