package piperkt.services.multimedia.application.api

import piperkt.services.multimedia.commons.events.UserCreated

interface UserEventsApi {
    fun onUserCreated(userCreated: UserCreated)
}
