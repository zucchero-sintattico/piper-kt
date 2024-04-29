package piperkt.services.users.interfaces.web.auth

import base.IntegrationTest
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.security.authentication.UsernamePasswordCredentials
import io.micronaut.security.token.render.BearerAccessRefreshToken
import piperkt.services.users.presentation.user.UserDTO

@Client("/")
interface AuthClient {
    @Post("/register") fun register(@Body request: RegisterController.RegisterRequest): UserDTO

    @Post("/login")
    fun login(
        @Body usernamePasswordCredentials: UsernamePasswordCredentials
    ): BearerAccessRefreshToken
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

        test("login") {
            val response = authClient.login(UsernamePasswordCredentials("user", "password"))
            response.accessToken shouldNotBe null
            response.refreshToken shouldNotBe null
            response.username shouldBe "user"
            response.roles shouldBe null
        }

        test("login with invalid password should throw AuthenticationException") {
            shouldThrow<HttpClientResponseException> {
                    authClient.login(UsernamePasswordCredentials("user", "invalid"))
                }
                .let {
                    it.status shouldBe HttpStatus.UNAUTHORIZED
                    it.message shouldBe "Credentials Do Not Match"
                }
        }

        test("login with invalid username should throw AuthenticationException") {
            shouldThrow<HttpClientResponseException> {
                    authClient.login(UsernamePasswordCredentials("invalid", "password"))
                }
                .let {
                    it.status shouldBe HttpStatus.UNAUTHORIZED
                    it.message shouldBe "User Not Found"
                }
        }
    })
