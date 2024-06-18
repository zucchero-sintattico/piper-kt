package piperkt.bdd.users

import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.kotest.matchers.shouldBe
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import piperkt.bdd.utils.PiperchatClient

@MicronautTest
class UserManagementSteps {

    var client = PiperchatClient()

    @Given("I am not logged in")
    fun iAmNotLoggedIn() {
        client.logout()
    }

    @When("I make a REGISTER request with valid credentials")
    fun iMakeAREGISTERRequestWithValidCredentials() {
        client.registerRandomUser()
    }

    @Then("I should be registered to the system")
    fun iShouldBeRegisteredToTheSystem() {
        client.isRegistered() shouldBe true
    }

    @Given("I am registered")
    fun iAmRegistered() {
        client.registerRandomUser()
    }

    @When("I make a LOGIN request with valid credentials")
    fun iMakeALOGINRequestWithValidCredentials() {
        client.login()
    }

    @Then("I should be logged in to the system")
    fun iShouldBeLoggedInToTheSystem() {
        client.isLoggedIn() shouldBe true
    }
}
