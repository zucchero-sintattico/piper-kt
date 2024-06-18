package piperkt.bdd.servers

import io.cucumber.java.en.And
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import piperkt.bdd.utils.PiperchatClient

@MicronautTest
class ServersSteps {

    private var client = PiperchatClient()
    private var otherClient = PiperchatClient()

    @Given("I am the logged user")
    fun iAmALoggedUser() {
        client.registerAndLogin()
    }

    @When("I create a new server")
    fun iCreateANewServer() {
        client.createServer()
    }

    @Then("I should be able to access the server as owner")
    fun iShouldBeAbleToAccessTheServer() {
        val response = client.myServers()
        val created = response.servers.find { it.id == client.getCreatedServerId() }
        created shouldNotBe null
        created!!.owner shouldBe client.getUsername()
    }

    @And("I have a server")
    fun iHaveAServer() {
        client.createServer()
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

    @And("another user has a server")
    fun anotherUserHasAServer() {
        otherClient.registerAndLogin()
        otherClient.createServer()
    }

    @When("I join the server")
    fun iJoinTheServer() {
        client.joinServer(otherClient.getCreatedServerId())
    }

    @Then("I should be able to access the server as member")
    fun iShouldBeAbleToAccessTheServerAsMember() {
        val response = client.myServers()
        val created = response.servers.find { it.id == otherClient.getCreatedServerId() }
        created shouldNotBe null
        created!!.owner shouldNotBe client.getUsername()
    }
}
