package piperkt.services.users.interfaces.web

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import java.security.Principal
import piperkt.services.users.application.UserDTO
import piperkt.services.users.application.UserService

@Controller
@Secured(SecurityRule.IS_AUTHENTICATED)
class UserController(private val userService: UserService) {

    @Get("/users/{username}")
    fun getUser(@PathVariable username: String): UserDTO {
        return userService.getUser(username)
    }

    @Get("/whoami")
    fun whoami(principal: Principal): UserDTO {
        return userService.getUser(principal.name)
    }
}
