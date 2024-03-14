package piperkt.services.multimedia.interfaces.web

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import piperkt.services.multimedia.application.MultimediaService
import piperkt.services.multimedia.application.Result
import piperkt.services.multimedia.application.api.query.GetUserInSessionQuery
import piperkt.services.multimedia.interfaces.web.api.GetUserInSessionApi

@Controller("/sessions")
class SessionControllerHTTP(private val multimediaService: MultimediaService) : SessionController {

    override fun getUsers(sessionId: String): HttpResponse<GetUserInSessionApi.Response> {
        return when (
            val result = multimediaService.getUsersInSession(GetUserInSessionQuery(sessionId))
        ) {
            is Result.Success -> GetUserInSessionApi.Response.fromSuccess(result.getSuccess())
            is Result.Error -> GetUserInSessionApi.Response.fromError(result.getError())
        }
    }
}
