package piperkt.services.users.interfaces.web

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import java.security.Principal
import piperkt.services.users.application.UserService
import piperkt.services.users.presentation.user.UserDTO
import piperkt.services.users.presentation.user.toDTO

@Controller
@Secured(SecurityRule.IS_AUTHENTICATED)
class UserController(private val userService: UserService) {

    @Get("/users/{username}")
    fun getUser(@PathVariable username: String): UserDTO {
        return userService.getUser(username).toDTO()
    }

    @Get("/whoami")
    fun whoami(principal: Principal): UserDTO {
        return userService.getUser(principal.name).toDTO()
    }
}
