package piperkt.services.multimedia.interfaces.web.api

import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Error
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Status
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.micronaut.serde.annotation.Serdeable
import java.security.Principal
import piperkt.services.multimedia.domain.session.SessionErrors

@Secured(SecurityRule.IS_AUTHENTICATED)
interface GetChannelSessionApi {

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
    operator fun invoke(
        principal: Principal,
        @PathVariable serverId: String,
        @PathVariable channelId: String
    ): Response

    @Error(SessionErrors.SessionNotFound::class)
    @Status(HttpStatus.NOT_FOUND)
    fun onChannelSessionNotFound(
        exception: SessionErrors.SessionNotFound,
        @PathVariable serverId: String,
        @PathVariable channelId: String
    ): Errors
}
