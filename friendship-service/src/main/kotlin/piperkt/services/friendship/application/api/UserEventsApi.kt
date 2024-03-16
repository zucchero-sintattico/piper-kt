package piperkt.services.friendship.application.api

import piperkt.services.friendship.commons.events.UserCreated

interface UserEventsApi {
    fun onUserCreated(userCreated: UserCreated)
}
