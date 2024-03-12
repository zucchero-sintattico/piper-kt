package piperkt.services.multimedia.interfaces.web

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import piperkt.services.multimedia.application.MultimediaService
import piperkt.services.multimedia.application.api.query.GetUserInSessionQuery
import piperkt.services.multimedia.interfaces.web.api.GetUsersInSessionResponse

@Controller("/sessions")
class SessionControllerHTTP(private val multimediaService: MultimediaService) : SessionController {
    override fun getUsers(sessionId: String): HttpResponse<GetUsersInSessionResponse> {
        val response = multimediaService.getUsersInSession(GetUserInSessionQuery(sessionId))
        return HttpResponse.ok(
            GetUsersInSessionResponse(response.users.map { it.username }.toSet())
        )
    }
}
