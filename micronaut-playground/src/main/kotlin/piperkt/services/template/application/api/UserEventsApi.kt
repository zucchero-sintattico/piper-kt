package piperkt.services.template.application.api

import piperkt.services.template.commons.events.UserCreated

interface UserEventsApi {
    fun onUserCreated(userCreated: UserCreated)
}
