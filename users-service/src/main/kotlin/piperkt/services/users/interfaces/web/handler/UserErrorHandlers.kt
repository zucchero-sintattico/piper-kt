package piperkt.services.users.interfaces.web.handler

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Error
import io.micronaut.http.annotation.Status
import io.micronaut.http.server.exceptions.ExceptionHandler
import io.micronaut.serde.annotation.Serdeable
import jakarta.inject.Singleton
import piperkt.services.users.domain.user.UserError

object UserErrorHandlers {
    @Serdeable data class ErrorResponse(val message: String)

    @Singleton
    class UserNotInServer : ExceptionHandler<UserError.UserNotFound, ErrorResponse> {
        @Error(
            global = true,
            exception = UserError.UserNotFound::class,
        )
        @Status(HttpStatus.NOT_FOUND)
        override fun handle(
            request: HttpRequest<*>?,
            exception: UserError.UserNotFound
        ): ErrorResponse {
            return ErrorResponse("User ${exception.username.value} not found")
        }
    }

    @Singleton
    class RefreshTokenNotFound : ExceptionHandler<UserError.RefreshTokenNotFound, ErrorResponse> {
        @Error(
            global = true,
            exception = UserError.RefreshTokenNotFound::class,
        )
        @Status(HttpStatus.NOT_FOUND)
        override fun handle(
            request: HttpRequest<*>?,
            exception: UserError.RefreshTokenNotFound
        ): ErrorResponse {
            return ErrorResponse("Refresh token ${exception.refreshToken} not found")
        }
    }

    @Singleton
    class UserAlreadyExists : ExceptionHandler<UserError.UserAlreadyExists, ErrorResponse> {
        @Error(
            global = true,
            exception = UserError.UserAlreadyExists::class,
        )
        @Status(HttpStatus.CONFLICT)
        override fun handle(
            request: HttpRequest<*>?,
            exception: UserError.UserAlreadyExists
        ): ErrorResponse {
            return ErrorResponse("User ${exception.username.value} already exists")
        }
    }

    @Singleton
    class InvalidPassword : ExceptionHandler<UserError.InvalidPassword, ErrorResponse> {
        @Error(
            global = true,
            exception = UserError.InvalidPassword::class,
        )
        @Status(HttpStatus.UNAUTHORIZED)
        override fun handle(
            request: HttpRequest<*>?,
            exception: UserError.InvalidPassword
        ): ErrorResponse {
            return ErrorResponse("Invalid password ${exception.password}")
        }
    }
}
