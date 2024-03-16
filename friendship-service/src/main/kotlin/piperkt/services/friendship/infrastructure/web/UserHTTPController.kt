package piperkt.services.friendship.infrastructure.web

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import piperkt.services.friendship.application.UserService
import piperkt.services.friendship.infrastructure.web.api.LoginRequest
import piperkt.services.friendship.infrastructure.web.api.LoginResponse
import piperkt.services.friendship.infrastructure.web.api.RegisterRequest
import piperkt.services.friendship.infrastructure.web.api.RegisterResponse

@Controller("/users")
class UserHTTPController(
    private val userService: UserService,
) {
    @Post("/login", consumes = ["application/json"], produces = ["application/json"])
    fun login(
        @Body request: LoginRequest,
    ): LoginResponse {
        val result = userService.loginUser(request.email, request.password)
        return LoginResponse(result?.email.orEmpty())
    }

    @Post("/register")
    fun register(
        @Body request: RegisterRequest,
    ): RegisterResponse {
        val result = userService.registerUser(request.email, request.password)
        return RegisterResponse(result.email)
    }
}
