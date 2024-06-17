package piperkt.bdd.users

import io.cucumber.java.en.And
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.micronaut.http.HttpHeaders
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.security.authentication.UsernamePasswordCredentials
import io.micronaut.security.token.render.BearerAccessRefreshToken
import io.micronaut.serde.annotation.Serdeable
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import java.net.URL

@Client("/")
interface UserControllerClient {

    @Serdeable
    data class UserDTO(
        val username: String,
        val email: String,
        val description: String,
        val profilePicture: String?,
    )

    @Get("whoami") fun whoami(@Header(HttpHeaders.AUTHORIZATION) authorization: String): UserDTO

    @Get("users/{username}")
    fun getUser(
        @PathVariable username: String,
        @Header(HttpHeaders.AUTHORIZATION) authorization: String,
    ): UserDTO

    @Serdeable
    data class RegisterRequest(
        val username: String,
        val password: String,
        val email: String?,
        val description: String?,
    )

    @Post("/auth/register") fun register(@Body request: RegisterRequest): UserDTO

    @Post("/auth/login")
    fun login(
        @Body usernamePasswordCredentials: UsernamePasswordCredentials,
    ): BearerAccessRefreshToken
}

@MicronautTest
class UserManagementSteps {

    var httpClient = HttpClient.create(URL("http://localhost:8080"))

    val templateUser = UserControllerClient.UserDTO("user", "email", "description", null)
    var user: UserControllerClient.UserDTO? = null
    var token: BearerAccessRefreshToken? = null

    @Given("I am not logged in")
    fun iAmNotLoggedIn() {
        user = null
        token = null
    }

    @When("I make a REGISTER request with valid credentials")
    fun iMakeAREGISTERRequestWithValidCredentials() {
        user = httpClient.toBlocking().exchange(Http)
    }

    @Then("I should be registered to the system")
    fun iShouldBeRegisteredToTheSystem() {
        true shouldBe false
        user shouldNotBe null
    }

    @Given("I am registered")
    fun iAmRegistered() {
        user =
            userControllerClient.register(
                UserControllerClient.RegisterRequest("user", "password", "email", "description")
            )
    }

    @When("I make a LOGIN request with valid credentials")
    fun iMakeALOGINRequestWithValidCredentials() {
        token = userControllerClient.login(UsernamePasswordCredentials("user", "password"))
    }

    @Then("I should be logged in to the system")
    fun iShouldBeLoggedInToTheSystem() {
        token shouldNotBe null
    }

    @And("I am logged in")
    fun iAmLoggedIn() {
        user =
            userControllerClient.register(
                UserControllerClient.RegisterRequest("user", "password", "email", "description")
            )
        token = userControllerClient.login(UsernamePasswordCredentials("user", "password"))
    }
}
