package piperkt.services.multimedia.interfaces.web.controllers

import io.micronaut.http.annotation.Controller
import java.security.Principal
import piperkt.services.multimedia.application.services.ServerService
import piperkt.services.multimedia.domain.server.ChannelId
import piperkt.services.multimedia.domain.server.ServerId
import piperkt.services.multimedia.domain.session.SessionErrors
import piperkt.services.multimedia.interfaces.web.api.GetChannelSessionApi

@Controller
class GetChannelSessionController(private val serverService: ServerService) : GetChannelSessionApi {

    override fun invoke(
        principal: Principal,
        serverId: String,
        channelId: String
    ): GetChannelSessionApi.Response {
        val server = serverService.getServer(ServerId(serverId))
        val channel = server.getChannelById(ChannelId(channelId))
        return GetChannelSessionApi.Response(channel.sessionId.value)
    }

    override fun onChannelSessionNotFound(
        exception: SessionErrors.SessionNotFound,
        serverId: String,
        channelId: String
    ): GetChannelSessionApi.Errors {
        return GetChannelSessionApi.Errors.ChannelSessionNotFound(channelId)
    }
}
