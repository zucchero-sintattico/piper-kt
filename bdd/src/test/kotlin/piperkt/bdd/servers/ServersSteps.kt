package piperkt.bdd.servers

import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.kotest.matchers.collections.shouldContain
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.security.authentication.UsernamePasswordCredentials
import io.micronaut.security.token.render.BearerAccessRefreshToken
import io.micronaut.serde.annotation.Serdeable
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import java.net.URL
import java.util.*
import piperkt.bdd.users.RegisterRequest
import piperkt.bdd.users.UserDTO

@MicronautTest
class ServersSteps {

    @Serdeable
    data class ServerDTO(
        val id: String,
    )

    var httpClient = HttpClient.create(URL("http://localhost:10000"))

    var user: UserDTO? = null
    var userToken: BearerAccessRefreshToken? = null
    var server: ServerDTO? = null

    fun randomUsername() = UUID.randomUUID().toString()

    @Given("I am the logged user")
    fun iAmALoggedUser() {
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

        userToken =
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

    @Serdeable
    data class ServerCreateRequest(
        val name: String,
        val description: String,
    )

    @Serdeable
    data class ServerCreateResponse(
        val serverId: String,
    )

    @When("I create a new server")
    fun iCreateANewServer() {
        server =
            httpClient
                .toBlocking()
                .exchange(
                    HttpRequest.POST("/servers", ServerCreateRequest("server", "description"))
                        .bearerAuth(userToken!!.accessToken),
                    ServerCreateResponse::class.java
                )
                .body()
                .let { ServerDTO(it.serverId) }
    }

    @Serdeable
    data class MyServersResponse(
        val servers: List<ServerDTO>,
    )

    @Then("I should be able to access the server")
    fun iShouldBeAbleToAccessTheServer() {
        val servers =
            httpClient
                .toBlocking()
                .exchange(
                    HttpRequest.GET<MyServersResponse>("/servers")
                        .bearerAuth(userToken!!.accessToken),
                    MyServersResponse::class.java
                )
                .body()

        servers.servers shouldContain server
    }
}
