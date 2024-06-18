package piperkt.bdd.servers

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

    @Given("I am the logged user")
    fun iAmALoggedUser() {
        client.registerRandomUser()
        client.login()
    }

    @When("I create a new server")
    fun iCreateANewServer() {
        client.createServer("serverName", "serverDescription")
    }

    @Then("I should be able to access the server")
    fun iShouldBeAbleToAccessTheServer() {
        val response = client.myServers()
        val created = response.servers.find { it.id == client.getCreatedServerId() }
        created shouldNotBe null
        created!!.owner shouldBe client.getUsername()
    }
}
