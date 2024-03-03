package micronaut.playground.infrastructure.web

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import micronaut.playground.application.UserService
import micronaut.playground.infrastructure.web.api.LoginRequest
import micronaut.playground.infrastructure.web.api.LoginResponse
import micronaut.playground.infrastructure.web.api.RegisterRequest
import micronaut.playground.infrastructure.web.api.RegisterResponse

@Controller("/users")
class UserHTTPController(private val userService: UserService) {

    @Post("/login", consumes = ["application/json"], produces = ["application/json"])
    fun login(@Body request: LoginRequest): LoginResponse {
        val result = userService.loginUser(request.email, request.password)
        return LoginResponse(result?.email ?: "")
    }

    @Post("/register")
    fun register(@Body request: RegisterRequest): RegisterResponse {
        val result = userService.registerUser(request.email, request.password)
        return RegisterResponse(result.email)
    }
}
