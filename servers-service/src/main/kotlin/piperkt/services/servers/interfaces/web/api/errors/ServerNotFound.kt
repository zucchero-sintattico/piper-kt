package piperkt.services.servers.interfaces.web.api.errors

import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Error
import io.micronaut.http.annotation.Produces
import io.micronaut.http.annotation.Status
import io.micronaut.http.server.exceptions.ExceptionHandler
import io.micronaut.serde.annotation.Serdeable
import jakarta.inject.Singleton
import piperkt.services.servers.application.exceptions.ServerNotFoundException

@Serdeable
data class ServerNotFoundResponse(
    val message: String,
)

@Produces
@Singleton
@Requires(classes = [ServerNotFoundException::class, ExceptionHandler::class])
class ServerNotFoundExceptionHandler :
    ExceptionHandler<ServerNotFoundException, ServerNotFoundResponse> {
    @Error(
        global = true,
        exception = ServerNotFoundException::class,
    )
    @Status(HttpStatus.NOT_FOUND)
    override fun handle(
        request: HttpRequest<*>?,
        exception: ServerNotFoundException?
    ): ServerNotFoundResponse {
        return ServerNotFoundResponse(exception!!.message)
    }
}
