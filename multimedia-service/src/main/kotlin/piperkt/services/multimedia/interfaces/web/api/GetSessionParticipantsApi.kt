package piperkt.services.multimedia.interfaces.web.api

import io.micronaut.http.HttpStatus
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
interface GetSessionParticipantsApi {

    @Serdeable data class Response(val users: Set<String>)

    sealed interface Errors {
        @Serdeable data class SessionNotFound(val sessionId: String) : Errors

        @Serdeable data class UserNotAllowed(val sessionId: String, val username: String) : Errors
    }

    @Get("/sessions/{sessionId}/users")
    @Status(HttpStatus.OK)
    fun get(principal: Principal, @PathVariable sessionId: String): Response

    @Error(SessionErrors.SessionNotFound::class)
    @Status(HttpStatus.NOT_FOUND)
    fun onSessionNotFound(
        exception: SessionErrors.SessionNotFound,
        @PathVariable sessionId: String,
    ): Errors.SessionNotFound

    @Error(SessionErrors.UserNotAllowed::class)
    @Status(HttpStatus.FORBIDDEN)
    fun onUserNotAllowed(
        exception: SessionErrors.UserNotAllowed,
        @PathVariable sessionId: String,
    ): Errors.UserNotAllowed
}
