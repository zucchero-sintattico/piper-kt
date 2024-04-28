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
    classes = [ServerServiceException.ChannelNotFoundException::class, ExceptionHandler::class]
)
class ChannelNotFound :
    ExceptionHandler<ServerServiceException.ChannelNotFoundException, ErrorResponse> {
    @Error(
        global = true,
        exception = ServerServiceException.ChannelNotFoundException::class,
    )
    @Status(HttpStatus.NOT_FOUND)
    override fun handle(
        request: HttpRequest<*>?,
        exception: ServerServiceException.ChannelNotFoundException?
    ): ErrorResponse {
        return ErrorResponse(exception!!.message)
    }
}
