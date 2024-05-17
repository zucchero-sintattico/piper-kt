package piperkt.services.multimedia.interfaces.web.controllers

import io.micronaut.http.annotation.Controller
import java.security.Principal
import piperkt.services.multimedia.application.session.SessionService
import piperkt.services.multimedia.domain.server.ChannelId
import piperkt.services.multimedia.domain.server.ServerErrors
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

    override fun onServerNotFound(
        exception: ServerErrors.ServerNotFound,
        serverId: String,
    ): GetChannelSessionApi.Errors.ServerNotFound {
        return GetChannelSessionApi.Errors.ServerNotFound(serverId)
    }

    override fun onChannelNotFound(
        exception: ServerErrors.ChannelNotInServer,
        serverId: String,
        channelId: String,
    ): GetChannelSessionApi.Errors.ChannelNotFound {
        return GetChannelSessionApi.Errors.ChannelNotFound(serverId, channelId)
    }

    override fun onUserNotAllowed(
        exception: ServerErrors.UserNotInServer,
        serverId: String,
        channelId: String,
    ): GetChannelSessionApi.Errors.UserNotAllowed {
        return GetChannelSessionApi.Errors.UserNotAllowed(
            serverId,
            channelId,
            exception.username.value
        )
    }
}
