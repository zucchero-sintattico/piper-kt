package piperkt.services.multimedia.interfaces.web

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Get
import piperkt.services.multimedia.interfaces.web.api.GetUsersInSessionResponse

interface SessionController {
    @Get("/{sessionId}/users")
    fun getUsers(sessionId: String): HttpResponse<GetUsersInSessionResponse>
}
