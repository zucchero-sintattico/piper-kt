package piperkt.services.users.interfaces.web.auth

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.micronaut.serde.annotation.Serdeable
import piperkt.services.users.application.AuthService
import piperkt.services.users.presentation.user.UserDTO
import piperkt.services.users.presentation.user.UserMapper.toDTO

/**
 * Controller for registering a new user.
 *
 * @param authService The service to handle the registration.
 */
@Controller("/register")
@Secured(SecurityRule.IS_ANONYMOUS)
class RegisterController(private val authService: AuthService) {

    @Serdeable
    data class RegisterRequest(
        val username: String,
        val password: String,
        val description: String? = null,
        val profilePicture: String? = null
    )

    @Post
    fun register(@Body request: RegisterRequest): UserDTO =
        authService
            .register(
                request.username,
                request.password,
                request.description,
                request.profilePicture
            )
            .toDTO()
}
