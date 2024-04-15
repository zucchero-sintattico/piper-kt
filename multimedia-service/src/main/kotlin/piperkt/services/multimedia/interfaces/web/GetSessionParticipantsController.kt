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
import piperkt.services.multimedia.application.services.SessionService
import piperkt.services.multimedia.domain.session.SessionErrors
import piperkt.services.multimedia.domain.session.SessionId

@Controller
@Secured(SecurityRule.IS_AUTHENTICATED)
class GetSessionParticipantsController(private val sessionService: SessionService) {

    @Serdeable data class Response(val users: Set<String>)

    @Serdeable
    sealed interface Errors {
        @Serdeable data class SessionNotFound(val sessionId: String) : Errors
    }

    @Get("/sessions/{sessionId}/users", produces = [MediaType.APPLICATION_JSON])
    @Status(HttpStatus.OK)
    fun get(principal: Principal, @PathVariable sessionId: String): Response {
        val session = sessionService.getSession(SessionId(sessionId))
        return Response(session.participants().map { it.value }.toSet())
    }

    @Error(SessionErrors.SessionNotFound::class)
    @Status(HttpStatus.NOT_FOUND)
    fun onSessionNotFound(
        exception: SessionErrors.SessionNotFound,
        @PathVariable sessionId: String
    ): Errors {
        return Errors.SessionNotFound(sessionId)
    }
}
