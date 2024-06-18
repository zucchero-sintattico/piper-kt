package piperkt.bdd.channels

import io.cucumber.java.en.And
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import piperkt.bdd.utils.PiperchatClient

class ChannelsSteps {
    private var client = PiperchatClient()

    @Given("I am an authenticated user")
    fun iAmAnAuthenticatedUser() {
        client.registerAndLogin()
    }

    @And("there is a channel in the server")
    fun thereIsAChannelInTheServer() {
        client.createChannel()
    }

    @And("I have created a server")
    fun iHaveAServer() {
        client.createServer()
    }

    @When("I remove the channel")
    fun iRemoveTheChannel() {
        client.deleteChannel(client.getCreatedServerId(), client.getCreatedChannelId())
    }

    @Then("the channel should be removed from the server")
    fun theChannelShouldBeRemovedFromTheServer() {
        val response = client.myServers()
        val server = response.servers.find { it.id == client.getCreatedServerId() }
        val channel = server!!.channels.find { it.id == client.getCreatedChannelId() }
        channel shouldBe null
    }

    @And("there is a text channel in the server")
    fun thereIsATextChannelInTheServer() {
        client.createChannel()
    }

    @When("I send a message in a text channel")
    fun iSendAMessage() {
        client.sendMessageInChannel(
            client.getCreatedServerId(),
            client.getCreatedChannelId(),
            "Hello, World!"
        )
    }

    @Then("the message should be displayed in the channel")
    fun theMessageShouldBeDisplayedInTheChannel() {
        val response = client.myServers()
        val server = response.servers.find { it.id == client.getCreatedServerId() }
        val channel = server!!.channels.find { it.id == client.getCreatedChannelId() }
        channel!!.messages.last().content shouldBe "Hello, World!"
    }

    @When("I create a new channel in a server")
    fun iCreateANewChannelInAServer() {
        client.createChannel()
    }

    @Then("I should be able to access the channel")
    fun iShouldBeAbleToAccessTheChannel() {
        val response = client.myServers()
        val server = response.servers.find { it.id == client.getCreatedServerId() }
        val channel = server!!.channels.find { it.id == client.getCreatedChannelId() }
        channel shouldNotBe null
    }
}
