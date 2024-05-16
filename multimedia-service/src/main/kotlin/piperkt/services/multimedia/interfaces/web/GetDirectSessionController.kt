package piperkt.services.multimedia.interfaces.web

import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
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
import piperkt.services.multimedia.domain.direct.DirectErrors
import piperkt.services.multimedia.domain.user.Username

@Controller
@Secured(SecurityRule.IS_AUTHENTICATED)
class GetDirectSessionController(private val sessionService: SessionService) {

    @Serdeable data class Response(val sessionId: String)

    @Serdeable
    sealed interface Errors {
        @Serdeable data class DirectSessionNotFound(val user: String) : Errors
    }

    @Get("/users/{username}/session", produces = [MediaType.APPLICATION_JSON])
    @Status(HttpStatus.OK)
    fun get(principal: Principal, @PathVariable username: String): Response {
        val directId =
            sessionService.getDirectSessionId(Username(principal.name), Username(username))
        return Response(directId.value)
    }

    @Error(DirectErrors.DirectNotFound::class)
    @Status(HttpStatus.NOT_FOUND)
    fun onDirectSessionNotFound(
        exception: DirectErrors.DirectNotFound,
        @PathVariable username: String,
    ): Errors {
        return Errors.DirectSessionNotFound(username)
    }
}
