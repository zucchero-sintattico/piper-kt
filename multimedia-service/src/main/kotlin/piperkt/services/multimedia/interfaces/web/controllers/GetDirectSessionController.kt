package piperkt.services.multimedia.interfaces.web.controllers

import io.micronaut.http.annotation.Controller
import java.security.Principal
import piperkt.services.multimedia.application.services.DirectService
import piperkt.services.multimedia.domain.direct.DirectId
import piperkt.services.multimedia.domain.session.SessionErrors
import piperkt.services.multimedia.domain.user.Username
import piperkt.services.multimedia.interfaces.web.api.GetDirectSessionApi

@Controller
class GetDirectSessionController(
    private val directService: DirectService,
) : GetDirectSessionApi {

    override fun invoke(principal: Principal, username: String): GetDirectSessionApi.Response {
        val direct =
            directService.getDirect(DirectId(setOf(Username(principal.name), Username(username))))
        return GetDirectSessionApi.Response(direct.sessionId.value)
    }

    override fun onDirectSessionNotFound(
        exception: SessionErrors.SessionNotFound,
        username: String
    ): GetDirectSessionApi.Errors {
        return GetDirectSessionApi.Errors.DirectSessionNotFound(username)
    }
}
