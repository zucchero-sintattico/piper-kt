package piperkt.services.users.interfaces.web.auth

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import java.security.Principal
import piperkt.services.users.application.AuthService

@Controller("/logout")
@Secured(SecurityRule.IS_AUTHENTICATED)
class LogoutController(private val authService: AuthService) {

    @Post
    fun logout(principal: Principal) {
        authService.logout(principal.name)
    }
}
