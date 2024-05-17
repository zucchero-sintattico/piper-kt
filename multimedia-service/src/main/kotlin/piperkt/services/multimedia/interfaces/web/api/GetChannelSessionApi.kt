package piperkt.services.multimedia.interfaces.web.api

import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Error
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Status
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.micronaut.serde.annotation.Serdeable
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import java.security.Principal
import piperkt.services.multimedia.domain.server.ServerErrors

@Secured(SecurityRule.IS_AUTHENTICATED)
interface GetChannelSessionApi {

    @Serdeable data class Response(val sessionId: String)

    sealed interface Errors {
        @Serdeable data class ServerNotFound(val serverId: String) : Errors

        @Serdeable data class ChannelNotFound(val serverId: String, val channelId: String) : Errors

        @Serdeable
        data class UserNotAllowed(
            val serverId: String,
            val channelId: String,
            val username: String,
        ) : Errors
    }

    @Get("/servers/{serverId}/channels/{channelId}/session")
    @Status(HttpStatus.OK)
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Session found successfully"),
        ApiResponse(responseCode = "404", description = "Server or channel not found"),
        ApiResponse(responseCode = "403", description = "User not allowed"),
    )
    fun get(
        principal: Principal,
        @PathVariable serverId: String,
        @PathVariable channelId: String,
    ): Response

    @Error(ServerErrors.ServerNotFound::class)
    @Status(HttpStatus.NOT_FOUND)
    fun onServerNotFound(
        exception: ServerErrors.ServerNotFound,
        @PathVariable serverId: String,
    ): Errors.ServerNotFound = Errors.ServerNotFound(serverId)

    @Error(ServerErrors.ChannelNotInServer::class)
    @Status(HttpStatus.NOT_FOUND)
    fun onChannelNotFound(
        exception: ServerErrors.ChannelNotInServer,
        @PathVariable serverId: String,
        @PathVariable channelId: String,
    ): Errors.ChannelNotFound = Errors.ChannelNotFound(serverId, channelId)

    @Error(ServerErrors.UserNotInServer::class)
    @Status(HttpStatus.FORBIDDEN)
    fun onUserNotAllowed(
        exception: ServerErrors.UserNotInServer,
        @PathVariable serverId: String,
        @PathVariable channelId: String,
    ): Errors.UserNotAllowed = Errors.UserNotAllowed(serverId, channelId, exception.username.value)
}
