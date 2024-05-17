package piperkt.services.users.interfaces.web.api

import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Error
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Status
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.micronaut.serde.annotation.Serdeable
import piperkt.services.users.domain.user.UserError
import piperkt.services.users.presentation.user.UserDTO

@Secured(SecurityRule.IS_ANONYMOUS)
interface RegisterApi {

    @Serdeable
    data class RegisterRequest(
        val username: String,
        val password: String,
        val description: String? = null,
        val profilePicture: String? = null,
    )

    sealed interface Errors {
        @Serdeable data class UserAlreadyExists(val username: String) : Errors
    }

    @Post("/register") fun register(@Body request: RegisterRequest): UserDTO

    @Error(UserError.UserAlreadyExists::class)
    @Status(HttpStatus.CONFLICT)
    fun onUserAlreadyExists(error: UserError.UserAlreadyExists): Errors.UserAlreadyExists {
        return Errors.UserAlreadyExists(error.username.value)
    }
}
