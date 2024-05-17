package piperkt.services.users.interfaces.web.api

import io.micronaut.http.annotation.Error
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.micronaut.serde.annotation.Serdeable
import java.security.Principal
import piperkt.services.users.domain.user.UserError
import piperkt.services.users.presentation.user.UserDTO

@Secured(SecurityRule.IS_AUTHENTICATED)
interface UserApi {

    @Get("/users/{username}") fun getUser(@PathVariable username: String): UserDTO

    @Get("/whoami") fun whoami(principal: Principal): UserDTO

    sealed interface Errors {
        @Serdeable data class UserNotFound(val username: String) : Errors
    }

    @Error(UserError.UserNotFound::class)
    fun onUserNotFound(error: UserError.UserNotFound): Errors.UserNotFound {
        return Errors.UserNotFound(error.username.value)
    }
}
