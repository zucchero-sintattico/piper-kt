package piperkt.services.multimedia.interfaces.web.controllers

import io.micronaut.http.annotation.Controller
import java.security.Principal
import piperkt.services.multimedia.application.session.SessionService
import piperkt.services.multimedia.domain.server.ChannelId
import piperkt.services.multimedia.domain.server.ServerId
import piperkt.services.multimedia.domain.user.Username
import piperkt.services.multimedia.interfaces.web.api.GetChannelSessionApi

@Controller
class GetChannelSessionController(private val sessionService: SessionService) :
    GetChannelSessionApi {

    override fun get(
        principal: Principal,
        serverId: String,
        channelId: String,
    ): GetChannelSessionApi.Response {
        val sessionId =
            sessionService.getChannelSessionId(
                author = Username(principal.name),
                ServerId(serverId),
                ChannelId(channelId)
            )
        return GetChannelSessionApi.Response(sessionId.value)
    }
}
