package piperkt.services.friendships.interfaces.web.api.errors

import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Error
import io.micronaut.http.annotation.Produces
import io.micronaut.http.annotation.Status
import io.micronaut.http.server.exceptions.ExceptionHandler
import jakarta.inject.Singleton
import piperkt.services.friendships.application.exceptions.FriendshipServiceException

@Produces
@Singleton
@Requires(
    classes =
        [FriendshipServiceException.UserNotHasPermissionsException::class, ExceptionHandler::class]
)
class UserNotHasPermissions :
    ExceptionHandler<FriendshipServiceException.UserNotHasPermissionsException, ErrorResponse> {
    @Error(
        global = true,
        exception = FriendshipServiceException.UserNotHasPermissionsException::class,
    )
    @Status(HttpStatus.FORBIDDEN)
    override fun handle(
        request: HttpRequest<*>?,
        exception: FriendshipServiceException.UserNotHasPermissionsException?
    ): ErrorResponse {
        return ErrorResponse(exception!!.message)
    }
}
