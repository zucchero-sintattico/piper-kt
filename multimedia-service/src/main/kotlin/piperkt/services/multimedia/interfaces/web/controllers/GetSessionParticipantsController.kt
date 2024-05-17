package piperkt.services.multimedia.interfaces.web.controllers

import io.micronaut.http.annotation.Controller
import java.security.Principal
import piperkt.services.multimedia.application.session.SessionService
import piperkt.services.multimedia.domain.session.SessionErrors
import piperkt.services.multimedia.domain.session.SessionId
import piperkt.services.multimedia.domain.user.Username
import piperkt.services.multimedia.interfaces.web.api.GetSessionParticipantsApi

@Controller
class GetSessionParticipantsController(private val sessionService: SessionService) :
    GetSessionParticipantsApi {

    override fun get(principal: Principal, sessionId: String): GetSessionParticipantsApi.Response {
        val participants =
            sessionService.getSessionParticipants(Username(principal.name), SessionId(sessionId))
        return GetSessionParticipantsApi.Response(participants.map { it.value }.toSet())
    }

    override fun onSessionNotFound(
        exception: SessionErrors.SessionNotFound,
        sessionId: String,
    ): GetSessionParticipantsApi.Errors.SessionNotFound {
        return GetSessionParticipantsApi.Errors.SessionNotFound(sessionId)
    }

    override fun onUserNotAllowed(
        exception: SessionErrors.UserNotAllowed,
        sessionId: String,
    ): GetSessionParticipantsApi.Errors.UserNotAllowed {
        return GetSessionParticipantsApi.Errors.UserNotAllowed(
            exception.sessionId.value,
            exception.username.value
        )
    }
}
