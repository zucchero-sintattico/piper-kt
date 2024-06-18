package piperkt.bdd.friendship

import io.cucumber.java.en.And
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldNotContain
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import piperkt.bdd.utils.PiperchatClient

@MicronautTest
class FriendshipsSteps {

    private val userClient = PiperchatClient()
    private val friendClient = PiperchatClient()

    @Given("I am a logged user")
    fun iAmALoggedUser() {
        userClient.registerRandomUser()
        userClient.login()
    }

    @And("another user exists")
    fun anotherUserExists() {
        friendClient.registerRandomUser()
        friendClient.login()
    }

    @When("I send a friend request to the other user")
    fun iSendAFriendRequestToTheOtherUser() {
        userClient.sendFriendRequest(friendClient.getUsername())
    }

    @Then("the other user should have a pending friend request")
    fun theOtherUserShouldHaveAPendingFriendRequest() {
        val friendRequests = friendClient.getFriendRequests()
        friendRequests.requests shouldContain userClient.getUsername()
    }

    @And("I have a pending friend request from the other user")
    fun iHaveAPendingFriendRequest() {
        friendClient.sendFriendRequest(userClient.getUsername())
    }

    @When("I accept the friend request")
    fun iAcceptTheFriendRequest() {
        userClient.acceptFriendRequest(friendClient.getUsername())
    }

    @Then("the other user should be added to my friend list")
    fun theOtherUserShouldBeAddedToMyFriendList() {
        val friends = userClient.getFriends()
        friends.friends shouldContain friendClient.getUsername()
    }

    @When("I reject the friend request")
    fun iRejectTheFriendRequest() {
        userClient.rejectFriendRequest(friendClient.getUsername())
    }

    @Then("the other user should not be added to my friend list")
    fun theOtherUserShouldNotBeAddedToMyFriendList() {
        val friends = userClient.getFriends()
        friends.friends shouldNotContain friendClient.getUsername()
    }
}
