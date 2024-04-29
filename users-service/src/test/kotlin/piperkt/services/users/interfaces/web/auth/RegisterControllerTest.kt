package piperkt.services.users.interfaces.web.auth

import base.IntegrationTest
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.micronaut.http.HttpHeaders
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import piperkt.services.users.presentation.user.UserDTO

@Client("/")
interface AuthClient {
    @Post("/register") fun register(@Body request: RegisterController.RegisterRequest): UserDTO

    @Post("/login") fun login(username: String, password: String)

    @Post("/logout") fun logout(@Header(HttpHeaders.AUTHORIZATION) authorization: String)
}

class RegisterControllerTest(private val authClient: AuthClient) :
    IntegrationTest.FunSpec({
        test("register") {
            val response =
                authClient.register(RegisterController.RegisterRequest("user", "password"))
            response.username shouldBe "user"
            response.description shouldBe ""
            response.profilePicture shouldBe byteArrayOf()
        }

        test("register with same username should throw UserAlreadyExistsException") {
            shouldThrow<HttpClientResponseException> {
                    authClient.register(RegisterController.RegisterRequest("user", "password"))
                }
                .let {
                    it.status shouldBe HttpStatus.CONFLICT
                    it.message shouldBe "User user already exists"
                }
        }

        test("login") { authClient.login("user", "password") }
    })
