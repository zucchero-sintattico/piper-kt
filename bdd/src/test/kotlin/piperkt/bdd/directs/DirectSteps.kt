package piperkt.bdd.directs

import io.cucumber.java.en.And
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
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
class DirectSteps {

    var httpClient = HttpClient.create(URL("http://localhost:10000"))

    var user: UserDTO? = null
    var friend: UserDTO? = null
    var userToken: BearerAccessRefreshToken? = null
    var friendToken: BearerAccessRefreshToken? = null

    fun randomUsername() = UUID.randomUUID().toString()

    @Serdeable
    data class FriendRequest(
        val receiver: String,
    )

    @Serdeable
    data class AcceptFriendRequest(
        val sender: String,
    )

    @Given("I am logged user")
    fun iAmLoggedUserWithAFriend() {
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

    @And("I have a friend")
    fun iHaveAFriend() {
        friend =
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
        friendToken =
            httpClient
                .toBlocking()
                .exchange(
                    HttpRequest.POST(
                        "/auth/login",
                        UsernamePasswordCredentials(friend!!.username, "password")
                    ),
                    BearerAccessRefreshToken::class.java
                )
                .body()
        httpClient
            .toBlocking()
            .exchange(
                HttpRequest.POST("/friends/requests/send", FriendRequest(friend!!.username))
                    .headers(mapOf("Authorization" to "Bearer ${userToken?.accessToken}")),
                String::class.java
            )
        httpClient
            .toBlocking()
            .exchange(
                HttpRequest.POST("/friends/requests/accept", AcceptFriendRequest(user!!.username))
                    .headers(mapOf("Authorization" to "Bearer ${friendToken?.accessToken}")),
                String::class.java
            )
    }

    @Serdeable
    data class NewMessage(
        val content: String,
    )

    @When("I send a message to the friend")
    fun iSendAMessageToTheFriend() {
        httpClient
            .toBlocking()
            .exchange(
                HttpRequest.POST("/users/${friend!!.username}/messages", NewMessage("Hello"))
                    .headers(mapOf("Authorization" to "Bearer ${userToken?.accessToken}")),
                String::class.java
            )
    }

    @Serdeable
    data class MessageDTO(
        val id: String,
        val content: String,
        val sender: String,
        val timestamp: String,
    )

    @Serdeable
    data class MessageResponse(
        val messages: List<MessageDTO>,
    )

    @Then("the friend should receive the message")
    fun theFriendShouldReceiveTheMessage() {
        val response =
            httpClient
                .toBlocking()
                .exchange(
                    HttpRequest.GET<MessageResponse>(
                            "/users/${friend!!.username}/messages?from=0&limit=10"
                        )
                        .headers(mapOf("Authorization" to "Bearer ${friendToken?.accessToken}")),
                    MessageResponse::class.java
                )
                .body()

        response shouldNotBe null
        response.messages.size shouldBe 1
        response.messages[0].content shouldBe "Hello"
    }
}
