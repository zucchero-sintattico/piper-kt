package piperkt.bdd.friendship

import io.cucumber.java.en.And
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldNotContain
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
class FriendshipsSteps {

    var httpClient = HttpClient.create(URL("http://localhost:10000"))

    var user: UserDTO? = null
    var friend: UserDTO? = null
    var userToken: BearerAccessRefreshToken? = null
    var friendToken: BearerAccessRefreshToken? = null

    fun randomUsername() = UUID.randomUUID().toString()

    @Given("I am a logged user")
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

    @And("another user exists")
    fun anotherUserExists() {
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
    }

    @Serdeable
    data class FriendRequest(
        val receiver: String,
    )

    @When("I send a friend request to the other user")
    fun iSendAFriendRequestToTheOtherUser() {
        httpClient
            .toBlocking()
            .retrieve(
                HttpRequest.POST(
                        "/friends/requests/send",
                        FriendRequest(friend!!.username),
                    )
                    .bearerAuth(userToken!!.accessToken),
            )
    }

    @Serdeable
    data class PendingFriendRequestResponse(
        val requests: List<String>,
    )

    @Then("the other user should have a pending friend request")
    fun theOtherUserShouldHaveAPendingFriendRequest() {
        val pending =
            httpClient
                .toBlocking()
                .retrieve(
                    HttpRequest.GET<PendingFriendRequestResponse>("/friends/requests")
                        .bearerAuth(friendToken!!.accessToken),
                    PendingFriendRequestResponse::class.java
                )
        pending.requests shouldContain user!!.username
    }

    @And("I have a pending friend request from the other user")
    fun iHaveAPendingFriendRequest() {
        httpClient
            .toBlocking()
            .retrieve(
                HttpRequest.POST(
                        "/friends/requests/send",
                        FriendRequest(user!!.username),
                    )
                    .bearerAuth(friendToken!!.accessToken),
            )
    }

    @Serdeable
    data class AcceptFriendRequest(
        val sender: String,
    )

    @When("I accept the friend request")
    fun iAcceptTheFriendRequest() {
        httpClient
            .toBlocking()
            .retrieve(
                HttpRequest.POST(
                        "/friends/requests/accept",
                        AcceptFriendRequest(friend!!.username),
                    )
                    .bearerAuth(userToken!!.accessToken),
            )
    }

    @Serdeable
    data class FriendsResponse(
        val friends: List<String>,
    )

    @Then("the other user should be added to my friend list")
    fun theOtherUserShouldBeAddedToMyFriendList() {
        val friends =
            httpClient
                .toBlocking()
                .retrieve(
                    HttpRequest.GET<FriendsResponse>("/friends")
                        .bearerAuth(userToken!!.accessToken),
                    FriendsResponse::class.java
                )
        friends.friends shouldContain friend!!.username
    }

    @When("I reject the friend request")
    fun iRejectTheFriendRequest() {
        httpClient
            .toBlocking()
            .retrieve(
                HttpRequest.POST(
                        "/friends/requests/decline",
                        AcceptFriendRequest(friend!!.username),
                    )
                    .bearerAuth(userToken!!.accessToken),
            )
    }

    @Then("the other user should not be added to my friend list")
    fun theOtherUserShouldNotBeAddedToMyFriendList() {
        val friends =
            httpClient
                .toBlocking()
                .retrieve(
                    HttpRequest.GET<FriendsResponse>("/friends")
                        .bearerAuth(userToken!!.accessToken),
                    FriendsResponse::class.java
                )
        friends.friends shouldNotContain friend!!.username
    }
}
