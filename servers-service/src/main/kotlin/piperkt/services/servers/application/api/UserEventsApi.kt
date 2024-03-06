package piperkt.services.servers.application.api

import piperkt.services.servers.commons.events.UserCreated

interface UserEventsApi {
    fun onUserCreated(userCreated: UserCreated)
}
