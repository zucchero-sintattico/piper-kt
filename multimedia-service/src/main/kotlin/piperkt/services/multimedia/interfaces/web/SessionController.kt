package piperkt.services.multimedia.interfaces.web

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import piperkt.services.multimedia.interfaces.web.api.GetUserInSessionApi

interface SessionController {
    @Get(GetUserInSessionApi.PATH)
    fun getUsers(@PathVariable sessionId: String): HttpResponse<GetUserInSessionApi.Response>
}
