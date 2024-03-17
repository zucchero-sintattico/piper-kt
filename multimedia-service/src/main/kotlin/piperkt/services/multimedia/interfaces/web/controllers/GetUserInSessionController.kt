package piperkt.services.multimedia.interfaces.web.controllers

import io.micronaut.http.annotation.Controller
import piperkt.services.multimedia.application.sessions.GetUsersInSessionUseCase
import piperkt.services.multimedia.interfaces.web.api.GetUsersInSessionApi

@Controller
class GetUserInSessionController(private val useCase: GetUsersInSessionUseCase) :
    GetUsersInSessionApi {

    override fun handle(sessionId: String): GetUsersInSessionApi.Response {
        println("sessionId: $sessionId")
        val response = useCase.handle(GetUsersInSessionUseCase.Query(sessionId)).getOrThrow()
        return GetUsersInSessionApi.Response(response.users)
    }

    override fun onError(
        exception: GetUsersInSessionUseCase.Errors,
        sessionId: String
    ): GetUsersInSessionApi.Errors {
        println("exception: $exception")
        return when (exception) {
            is GetUsersInSessionUseCase.Errors.SessionNotFound ->
                GetUsersInSessionApi.Errors.SessionNotFound(sessionId)
        }
    }
}
