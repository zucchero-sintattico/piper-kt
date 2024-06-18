package piperkt.bdd.directs

import io.cucumber.java.en.And
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import piperkt.bdd.utils.PiperchatClient

@MicronautTest
class DirectSteps {

    val userClient = PiperchatClient()
    val friendClient = PiperchatClient()

    @Given("I am logged user")
    fun iAmLoggedUserWithAFriend() {
        userClient.registerRandomUser()
        userClient.login()
    }

    @And("I have a friend")
    fun iHaveAFriend() {
        friendClient.registerRandomUser()
        friendClient.login()
        friendClient.sendFriendRequest(userClient.getUsername())
        userClient.acceptFriendRequest(friendClient.getUsername())
    }

    @When("I send a message to the friend")
    fun iSendAMessageToTheFriend() {
        userClient.sendMessageToFriend(friendClient.getUsername(), "Hello")
    }

    @Then("the friend should receive the message")
    fun theFriendShouldReceiveTheMessage() {
        val messages = friendClient.getMessagesFromFriend(userClient.getUsername())
        messages.messages shouldNotBe null
        messages.messages.size shouldBe 1
        messages.messages[0].content shouldBe "Hello"
    }
}
