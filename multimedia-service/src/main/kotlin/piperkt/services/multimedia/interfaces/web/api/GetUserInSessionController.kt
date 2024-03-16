package piperkt.services.multimedia.interfaces.web.api

import io.micronaut.http.annotation.Controller
import piperkt.services.multimedia.application.sessions.GetUsersInSessionUseCase

@Controller("/sessions/{sessionId}/users")
open class GetUserInSessionController(private val useCase: GetUsersInSessionUseCase) :
    GetUsersInSessionApi {
    override fun handle(sessionId: String): GetUsersInSessionApi.Response {
        val response = useCase.handle(GetUsersInSessionUseCase.Query(sessionId)).getOrThrow()
        return GetUsersInSessionApi.Response(response.users)
    }

    override fun onUserNotFound(
        exception: GetUsersInSessionUseCase.Errors.SessionNotFound
    ): GetUsersInSessionApi.NotFound {
        return GetUsersInSessionApi.NotFound(exception.sessionId)
    }
}
