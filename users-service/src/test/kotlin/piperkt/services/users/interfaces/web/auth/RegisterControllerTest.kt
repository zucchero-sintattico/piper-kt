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
import io.micronaut.security.endpoints.TokenRefreshRequest
import io.micronaut.security.token.render.AccessRefreshToken
import io.micronaut.security.token.render.BearerAccessRefreshToken
import piperkt.services.users.application.AuthService
import piperkt.services.users.domain.user.User
import piperkt.services.users.domain.user.UserRepository
import piperkt.services.users.interfaces.web.api.RegisterApi
import piperkt.services.users.presentation.user.UserDTO

@Client("/")
interface AuthClient {
    @Post("/auth/register") fun register(@Body request: RegisterApi.RegisterRequest): UserDTO

    @Post("/auth/login")
    fun login(
        @Body usernamePasswordCredentials: UsernamePasswordCredentials,
    ): BearerAccessRefreshToken

    @Post("/oauth/access_token")
    fun refresh(@Body tokenRefreshRequest: TokenRefreshRequest): AccessRefreshToken
}

@NoMockedAuthentication
class RegisterControllerTest(
    private val authClient: AuthClient,
    private val authService: AuthService,
    private val userRepository: UserRepository,
) :
    IntegrationTest.FunSpec({
        lateinit var user: User

        beforeEach { user = authService.register("user", "password") }

        afterEach { authService.delete(user.username.value) }

        test("register") {
            val response =
                authClient.register(
                    RegisterApi.RegisterRequest("newuser", "password", "email", "description")
                )
            response.username shouldBe "newuser"
            response.description shouldBe "description"
            response.email shouldBe "email"
            response.profilePicture shouldBe null
            userRepository.findAll().size shouldBe 2
            authService.delete("newuser")
        }

        test("register with same username should throw UserAlreadyExistsException") {
            shouldThrow<HttpClientResponseException> {
                    authClient.register(RegisterApi.RegisterRequest("user", "password"))
                }
                .let { it.status shouldBe HttpStatus.CONFLICT }
        }

        test("login") {
            val response = authClient.login(UsernamePasswordCredentials("user", "password"))
            response.accessToken shouldNotBe null
            response.refreshToken shouldNotBe null
            response.username shouldBe "user"
            response.roles shouldBe null
            userRepository.findAll().size shouldBe 2
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

        test("refresh") {
            val loginResponse = authClient.login(UsernamePasswordCredentials("user", "password"))
            Thread.sleep(1000) // wait for the iat to be different
            val response =
                authClient.refresh(
                    TokenRefreshRequest(
                        TokenRefreshRequest.GRANT_TYPE_REFRESH_TOKEN,
                        loginResponse.refreshToken
                    )
                )
            response.accessToken shouldNotBe loginResponse.accessToken
            response.refreshToken shouldBe loginResponse.refreshToken
        }
    })
