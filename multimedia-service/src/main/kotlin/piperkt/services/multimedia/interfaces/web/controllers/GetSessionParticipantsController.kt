package piperkt.services.multimedia.interfaces.web.controllers

import io.micronaut.http.annotation.Controller
import piperkt.services.multimedia.application.sessions.GetSessionParticipantsUseCase
import piperkt.services.multimedia.interfaces.web.api.GetSessionParticipantsApi

@Controller
class GetSessionParticipantsController(private val useCase: GetSessionParticipantsUseCase) :
    GetSessionParticipantsApi {

    override fun handle(sessionId: String): GetSessionParticipantsApi.Response {
        val response = useCase.handle(GetSessionParticipantsUseCase.Query(sessionId)).getOrThrow()
        return GetSessionParticipantsApi.Response(response.users)
    }

    override fun onError(
        exception: GetSessionParticipantsUseCase.Errors,
        sessionId: String
    ): GetSessionParticipantsApi.Errors {
        return when (exception) {
            is GetSessionParticipantsUseCase.Errors.SessionNotFound ->
                GetSessionParticipantsApi.Errors.SessionNotFound(sessionId)
        }
    }
}
