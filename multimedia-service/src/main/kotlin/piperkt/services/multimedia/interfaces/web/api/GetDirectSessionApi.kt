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
import piperkt.services.multimedia.domain.direct.DirectErrors

@Secured(SecurityRule.IS_AUTHENTICATED)
interface GetDirectSessionApi {

    @Serdeable data class Response(val sessionId: String)

    sealed interface Errors {
        @Serdeable data class DirectSessionNotFound(val user: String) : Errors
    }

    @Get("/users/{username}/session")
    @Status(HttpStatus.OK)
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Direct session found successfully"),
        ApiResponse(responseCode = "404", description = "Direct session not found"),
    )
    fun get(principal: Principal, @PathVariable username: String): Response

    @Error(DirectErrors.DirectNotFound::class)
    @Status(HttpStatus.NOT_FOUND)
    fun onDirectSessionNotFound(
        exception: DirectErrors.DirectNotFound,
        @PathVariable username: String,
    ): Errors.DirectSessionNotFound
}
