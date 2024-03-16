package piperkt.services.multimedia.interfaces.web.controllers

import io.micronaut.http.annotation.Controller
import piperkt.services.multimedia.application.sessions.GetUsersInSessionUseCase
import piperkt.services.multimedia.interfaces.web.api.GetUsersInSessionApi

@Controller("/sessions/{sessionId}/users")
class GetUserInSessionController(private val useCase: GetUsersInSessionUseCase) :
    GetUsersInSessionApi {

    override fun handle(sessionId: String): GetUsersInSessionApi.Response {
        val response = useCase.handle(GetUsersInSessionUseCase.Query(sessionId)).getOrThrow()
        return GetUsersInSessionApi.Response(response.users)
    }

    override fun onError(
        exception: GetUsersInSessionUseCase.Errors,
        sessionId: String
    ): GetUsersInSessionApi.Errors =
        when (exception) {
            is GetUsersInSessionUseCase.Errors.SessionNotFound ->
                GetUsersInSessionApi.Errors.SessionNotFound(sessionId)
        }
}
