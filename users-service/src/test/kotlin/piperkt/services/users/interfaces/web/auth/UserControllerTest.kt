package piperkt.services.users.interfaces.web.auth

import base.IntegrationTest
import io.kotest.matchers.shouldBe
import io.micronaut.http.HttpHeaders
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.client.annotation.Client
import piperkt.services.users.application.AuthService
import piperkt.services.users.interfaces.web.auth.TestUtils.authOf
import piperkt.services.users.presentation.user.UserDTO
import piperkt.services.users.presentation.user.UserMapper.toDTO

@Client("/")
interface UserControllerClient {

    @Get("whoami") fun whoami(@Header(HttpHeaders.AUTHORIZATION) authorization: String): UserDTO

    @Get("users/{username}")
    fun getUser(
        @PathVariable username: String,
        @Header(HttpHeaders.AUTHORIZATION) authorization: String
    ): UserDTO
}

class UserControllerTest(
    private val userControllerClient: UserControllerClient,
    private val authService: AuthService
) :
    IntegrationTest.FunSpec({
        val user = authService.register("user", "password")
        val otherUser = authService.register("otherUser", "password")

        test("whoami") {
            val response = userControllerClient.whoami(authOf(user.username.value))
            response shouldBe user.toDTO()
        }

        test("getUser") {
            val response =
                userControllerClient.getUser(otherUser.username.value, authOf(user.username.value))
            response shouldBe otherUser.toDTO()
        }
    })
