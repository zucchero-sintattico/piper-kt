package piperkt.services.users.interfaces.web

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import java.security.Principal
import piperkt.services.users.application.UserService
import piperkt.services.users.presentation.user.UserDTO
import piperkt.services.users.presentation.user.UserMapper.toDTO

/**
 * Controller for the user operations.
 *
 * @param userService The service to handle the user operations.
 */
@Controller
@Secured(SecurityRule.IS_AUTHENTICATED)
class UserController(private val userService: UserService) {

    /**
     * Get a user by username.
     *
     * @param username The username of the user to get.
     * @return The user with the given username.
     */
    @Get("/users/{username}")
    fun getUser(@PathVariable username: String): UserDTO {
        return userService.getUser(username).toDTO()
    }

    /**
     * Get the current user.
     *
     * @param principal The username of the current user.
     * @return The current user.
     */
    @Get("/whoami")
    fun whoami(principal: Principal): UserDTO {
        return userService.getUser(principal.name).toDTO()
    }
}
