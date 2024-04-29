package piperkt.services.users.interfaces.web.auth

import base.IntegrationTest
import io.kotest.matchers.shouldBe
import io.micronaut.http.HttpHeaders
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.http.client.annotation.Client
import piperkt.services.users.interfaces.web.auth.TestUtils.authOf

@Client("/")
interface UserControllerClient {
    val auth: String
        get() = authOf("user")

    @Get("whoami") fun whoami(@Header(HttpHeaders.AUTHORIZATION) authorization: String = auth)

    @Get("users/{username}")
    fun getUser(username: String, @Header(HttpHeaders.AUTHORIZATION) authorization: String = auth)
}

class UserControllerTest(private val userControllerClient: UserControllerClient) :
    IntegrationTest.FunSpec({
        test("whoami") {
            val response = userControllerClient.whoami()
            response shouldBe "user"
        }
    })
