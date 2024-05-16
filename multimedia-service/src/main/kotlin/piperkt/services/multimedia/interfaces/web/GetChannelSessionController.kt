package piperkt.services.multimedia.interfaces.web

import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Error
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Status
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.micronaut.serde.annotation.Serdeable
import java.security.Principal
import piperkt.services.multimedia.application.session.SessionService
import piperkt.services.multimedia.domain.server.ChannelId
import piperkt.services.multimedia.domain.server.ServerErrors
import piperkt.services.multimedia.domain.server.ServerId
import piperkt.services.multimedia.domain.user.Username

@Controller
@Secured(SecurityRule.IS_AUTHENTICATED)
class GetChannelSessionController(private val sessionService: SessionService) {

    @Serdeable data class Response(val sessionId: String)

    @Serdeable
    sealed interface Errors {
        @Serdeable data class ServerNotFound(val serverId: String) : Errors

        @Serdeable data class ChannelNotFound(val serverId: String, val channelId: String) : Errors

        @Serdeable
        data class UserNotAllowed(
            val serverId: String,
            val channelId: String,
            val username: String
        ) : Errors
    }

    @Get("/servers/{serverId}/channels/{channelId}/session")
    @Status(HttpStatus.OK)
    fun get(
        principal: Principal,
        @PathVariable serverId: String,
        @PathVariable channelId: String,
    ): Response {
        val sessionId =
            sessionService.getChannelSessionId(
                author = Username(principal.name),
                ServerId(serverId),
                ChannelId(channelId)
            )
        return Response(sessionId.value)
    }

    @Error(ServerErrors.ServerNotFound::class)
    @Status(HttpStatus.NOT_FOUND)
    fun onServerNotFound(
        exception: ServerErrors.ServerNotFound,
        @PathVariable serverId: String,
    ): Errors {
        return Errors.ServerNotFound(serverId)
    }

    @Error(ServerErrors.ChannelNotInServer::class)
    @Status(HttpStatus.NOT_FOUND)
    fun onChannelNotFound(
        exception: ServerErrors.ChannelNotInServer,
        @PathVariable serverId: String,
        @PathVariable channelId: String,
    ): Errors {
        return Errors.ChannelNotFound(serverId, channelId)
    }

    @Error(ServerErrors.UserNotInServer::class)
    @Status(HttpStatus.FORBIDDEN)
    fun onUserNotAllowed(
        exception: ServerErrors.UserNotInServer,
        @PathVariable serverId: String,
        @PathVariable channelId: String,
    ): Errors {
        return Errors.UserNotAllowed(serverId, channelId, exception.username.value)
    }
}
