package piperkt.services.users.interfaces.web

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import java.security.Principal
import piperkt.services.users.application.UserService

@Controller("/profile")
@Secured(SecurityRule.IS_AUTHENTICATED)
class ProfileController(private val userService: UserService) {

    @Post("/description")
    fun updateDescription(principal: Principal, description: String) {
        return userService.updateUserDescription(principal.name, description)
    }

    @Post("/photo")
    fun updateProfilePicture(principal: Principal, profilePicture: ByteArray) {
        return userService.updateUserProfilePicture(principal.name, profilePicture)
    }
}
