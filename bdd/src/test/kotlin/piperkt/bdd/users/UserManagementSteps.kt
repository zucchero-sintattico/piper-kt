package piperkt.bdd.users

import io.cucumber.java.en.And
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.kotest.matchers.shouldNotBe
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.security.authentication.UsernamePasswordCredentials
import io.micronaut.security.token.render.BearerAccessRefreshToken
import io.micronaut.serde.annotation.Serdeable
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import java.net.URL
import java.util.*

@Serdeable
data class UserDTO(
    val username: String,
    val email: String,
    val description: String,
    val profilePicture: String?,
)

@Serdeable
data class RegisterRequest(
    val username: String,
    val password: String,
    val email: String?,
    val description: String?,
)

@MicronautTest
class UserManagementSteps {

    var httpClient = HttpClient.create(URL("http://localhost:10000"))

    var user: UserDTO? = null
    var token: BearerAccessRefreshToken? = null

    fun randomUsername() = UUID.randomUUID().toString()

    @Given("I am not logged in")
    fun iAmNotLoggedIn() {
        token = null
    }

    @When("I make a REGISTER request with valid credentials")
    fun iMakeAREGISTERRequestWithValidCredentials() {
        user =
            httpClient
                .toBlocking()
                .exchange(
                    HttpRequest.POST(
                        "/auth/register",
                        RegisterRequest(randomUsername(), "password", "email", "description")
                    ),
                    UserDTO::class.java
                )
                .body()
    }

    @Then("I should be registered to the system")
    fun iShouldBeRegisteredToTheSystem() {
        user shouldNotBe null
    }

    @Given("I am registered")
    fun iAmRegistered() {
        user =
            httpClient
                .toBlocking()
                .exchange(
                    HttpRequest.POST(
                        "/auth/register",
                        RegisterRequest(randomUsername(), "password", "email", "description")
                    ),
                    UserDTO::class.java
                )
                .body()
    }

    @When("I make a LOGIN request with valid credentials")
    fun iMakeALOGINRequestWithValidCredentials() {
        token =
            httpClient
                .toBlocking()
                .exchange(
                    HttpRequest.POST(
                        "/auth/login",
                        UsernamePasswordCredentials(user!!.username, "password")
                    ),
                    BearerAccessRefreshToken::class.java
                )
                .body()
    }

    @Then("I should be logged in to the system")
    fun iShouldBeLoggedInToTheSystem() {
        token shouldNotBe null
    }

    @And("I am logged in")
    fun iAmLoggedIn() {
        user =
            httpClient
                .toBlocking()
                .exchange(
                    HttpRequest.POST(
                        "/auth/register",
                        RegisterRequest(randomUsername(), "password", "email", "description")
                    ),
                    UserDTO::class.java
                )
                .body()
        token =
            httpClient
                .toBlocking()
                .exchange(
                    HttpRequest.POST(
                        "/auth/login",
                        UsernamePasswordCredentials(user!!.username, "password")
                    ),
                    BearerAccessRefreshToken::class.java
                )
                .body()
    }
}
