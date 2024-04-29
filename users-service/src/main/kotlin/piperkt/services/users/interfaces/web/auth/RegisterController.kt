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

@Controller("/register")
@Secured(SecurityRule.IS_ANONYMOUS)
class RegisterController(private val authService: AuthService) {

    @Serdeable
    data class RegisterRequest(
        val username: String,
        val password: String,
        val description: String = "",
        val photo: ByteArray = byteArrayOf()
    )

    @Post
    fun register(@Body request: RegisterRequest): UserDTO =
        authService
            .register(request.username, request.password, request.description, request.photo)
            .toDTO()
}
