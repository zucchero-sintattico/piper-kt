package piperkt.services.multimedia.interfaces.web.controllers

import io.micronaut.http.annotation.Controller
import java.security.Principal
import piperkt.services.multimedia.application.session.SessionService
import piperkt.services.multimedia.domain.user.Username
import piperkt.services.multimedia.interfaces.web.api.GetDirectSessionApi

@Controller
class GetDirectSessionController(private val sessionService: SessionService) : GetDirectSessionApi {

    override fun get(principal: Principal, username: String): GetDirectSessionApi.Response {
        val directId =
            sessionService.getDirectSessionId(Username(principal.name), Username(username))
        return GetDirectSessionApi.Response(directId.value)
    }
}
