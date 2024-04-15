package piperkt.services.multimedia.interfaces.web.controllers

import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Error
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Status
import io.micronaut.serde.annotation.Serdeable
import java.security.Principal
import piperkt.services.multimedia.application.services.ServerService
import piperkt.services.multimedia.domain.server.ChannelId
import piperkt.services.multimedia.domain.server.ServerId
import piperkt.services.multimedia.domain.session.SessionErrors

@Controller
class GetChannelSessionController(private val serverService: ServerService) {

    @Serdeable data class Response(val sessionId: String)

    @Serdeable
    sealed interface Errors {
        @Serdeable data class ChannelSessionNotFound(val channelId: String) : Errors
    }

    @Get(
        "/servers/{serverId}/channels/{channelId}/session",
        produces = [MediaType.APPLICATION_JSON]
    )
    @Status(HttpStatus.OK)
    fun get(
        principal: Principal,
        @PathVariable serverId: String,
        @PathVariable channelId: String
    ): Response {
        val server = serverService.getServer(ServerId(serverId))
        val channel = server.getChannelById(ChannelId(channelId))
        return Response(channel.sessionId.value)
    }

    @Error(SessionErrors.SessionNotFound::class)
    @Status(HttpStatus.NOT_FOUND)
    fun onChannelSessionNotFound(
        exception: SessionErrors.SessionNotFound,
        serverId: String,
        channelId: String
    ): Errors {
        return Errors.ChannelSessionNotFound(channelId)
    }
}
