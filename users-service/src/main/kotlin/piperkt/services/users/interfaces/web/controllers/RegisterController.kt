package piperkt.services.users.interfaces.web.controllers

import io.micronaut.http.annotation.Controller
import piperkt.services.users.application.AuthService
import piperkt.services.users.interfaces.web.api.RegisterApi
import piperkt.services.users.presentation.user.UserDTO
import piperkt.services.users.presentation.user.UserMapper.toDTO

/**
 * Controller for registering a new user.
 *
 * @param authService The service to handle the registration.
 */
@Controller
class RegisterController(private val authService: AuthService) : RegisterApi {

    override fun register(request: RegisterApi.RegisterRequest): UserDTO =
        authService
            .register(
                request.username,
                request.password,
                request.description,
                request.profilePicture
            )
            .toDTO()
}
