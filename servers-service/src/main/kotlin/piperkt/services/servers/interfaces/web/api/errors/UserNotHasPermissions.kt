package piperkt.services.servers.interfaces.web.api.errors

import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Error
import io.micronaut.http.annotation.Produces
import io.micronaut.http.annotation.Status
import io.micronaut.http.server.exceptions.ExceptionHandler
import jakarta.inject.Singleton
import piperkt.services.servers.application.exceptions.ServerService

@Produces
@Singleton
@Requires(classes = [ServerService.UserNotHasPermissionsException::class, ExceptionHandler::class])
class UserNotHasPermissions :
    ExceptionHandler<ServerService.UserNotHasPermissionsException, ErrorResponse> {
    @Error(
        global = true,
        exception = ServerService.UserNotHasPermissionsException::class,
    )
    @Status(HttpStatus.FORBIDDEN)
    override fun handle(
        request: HttpRequest<*>?,
        exception: ServerService.UserNotHasPermissionsException?
    ): ErrorResponse {
        return ErrorResponse(exception!!.message)
    }
}
