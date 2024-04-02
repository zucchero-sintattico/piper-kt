package piperkt.services.multimedia.interfaces.web.controllers

import io.micronaut.http.annotation.Controller
import piperkt.services.multimedia.application.usecases.GetSessionParticipants
import piperkt.services.multimedia.domain.session.SessionErrors
import piperkt.services.multimedia.domain.session.SessionId
import piperkt.services.multimedia.interfaces.web.api.GetSessionParticipantsApi

@Controller
class GetSessionParticipantsController(private val useCase: GetSessionParticipants) :
    GetSessionParticipantsApi {

    override fun invoke(sessionId: String): GetSessionParticipantsApi.Response {
        val response =
            useCase.invoke(GetSessionParticipants.Query(SessionId(sessionId))).getOrThrow()
        return GetSessionParticipantsApi.Response(response.usersIds.map { it.value }.toSet())
    }

    override fun onSessionNotFound(
        exception: SessionErrors.SessionNotFound,
        sessionId: String
    ): GetSessionParticipantsApi.Errors {
        return GetSessionParticipantsApi.Errors.SessionNotFound(sessionId)
    }
}
