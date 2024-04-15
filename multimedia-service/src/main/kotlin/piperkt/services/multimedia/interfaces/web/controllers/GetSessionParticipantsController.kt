package piperkt.services.multimedia.interfaces.web.controllers

import io.micronaut.http.annotation.Controller
import java.security.Principal
import piperkt.services.multimedia.application.services.SessionService
import piperkt.services.multimedia.domain.session.SessionErrors
import piperkt.services.multimedia.domain.session.SessionId
import piperkt.services.multimedia.interfaces.web.api.GetSessionParticipantsApi

@Controller
class GetSessionParticipantsController(private val sessionService: SessionService) :
    GetSessionParticipantsApi {

    override fun invoke(
        principal: Principal,
        sessionId: String
    ): GetSessionParticipantsApi.Response {
        val session = sessionService.getSession(SessionId(sessionId))
        return GetSessionParticipantsApi.Response(session.participants().map { it.value }.toSet())
    }

    override fun onSessionNotFound(
        exception: SessionErrors.SessionNotFound,
        sessionId: String
    ): GetSessionParticipantsApi.Errors {
        return GetSessionParticipantsApi.Errors.SessionNotFound(sessionId)
    }
}
