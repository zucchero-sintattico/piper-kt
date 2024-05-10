package piperkt.services.servers.interfaces.web.api.errors

import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Error
import io.micronaut.http.annotation.Produces
import io.micronaut.http.annotation.Status
import io.micronaut.http.server.exceptions.ExceptionHandler
import jakarta.inject.Singleton
import piperkt.services.servers.application.exceptions.ServerServiceException

@Produces
@Singleton
@Requires(
    classes =
        [ServerServiceException.OwnerCannotLeaveServerException::class, ExceptionHandler::class]
)
class OwnerCannotLeaveServer :
    ExceptionHandler<ServerServiceException.OwnerCannotLeaveServerException, ErrorResponse> {
    @Error(
        global = true,
        exception = ServerServiceException.OwnerCannotLeaveServerException::class,
    )
    @Status(HttpStatus.UNPROCESSABLE_ENTITY)
    override fun handle(
        request: HttpRequest<*>?,
        exception: ServerServiceException.OwnerCannotLeaveServerException?
    ): ErrorResponse {
        return ErrorResponse(exception!!.message)
    }
}
