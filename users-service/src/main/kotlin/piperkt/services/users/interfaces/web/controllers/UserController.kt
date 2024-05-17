package piperkt.services.users.interfaces.web.controllers

import io.micronaut.http.annotation.Controller
import java.security.Principal
import piperkt.services.users.application.UserService
import piperkt.services.users.interfaces.web.api.UserApi
import piperkt.services.users.presentation.user.UserDTO
import piperkt.services.users.presentation.user.UserMapper.toDTO

/**
 * Controller for the user operations.
 *
 * @param userService The service to handle the user operations.
 */
@Controller
class UserController(private val userService: UserService) : UserApi {

    /**
     * Get a user by username.
     *
     * @param username The username of the user to get.
     * @return The user with the given username.
     */
    override fun getUser(username: String): UserDTO {
        return userService.getUser(username).toDTO()
    }

    /**
     * Get the current user.
     *
     * @param principal The username of the current user.
     * @return The current user.
     */
    override fun whoami(principal: Principal): UserDTO {
        return userService.getUser(principal.name).toDTO()
    }
}
