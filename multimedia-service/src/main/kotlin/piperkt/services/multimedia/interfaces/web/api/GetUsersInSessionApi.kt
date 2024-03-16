package piperkt.services.multimedia.interfaces.web.api

import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Error
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Status
import io.micronaut.serde.annotation.Serdeable
import piperkt.services.multimedia.application.sessions.GetUsersInSessionUseCase

@Controller("/sessions/{sessionId}/users")
interface GetUsersInSessionApi {

    @Serdeable data class Response(val users: Set<String>)

    @Serdeable data class NotFound(val sessionId: String)

    @Get @Status(HttpStatus.OK) fun handle(@PathVariable sessionId: String): Response

    @Error(GetUsersInSessionUseCase.Errors.SessionNotFound::class)
    @Status(HttpStatus.NOT_FOUND)
    fun onUserNotFound(exception: GetUsersInSessionUseCase.Errors.SessionNotFound): NotFound
}
