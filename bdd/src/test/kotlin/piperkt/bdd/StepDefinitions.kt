package piperkt.bdd

import io.cucumber.java.en.Given
import io.kotest.core.spec.style.AnnotationSpec

class StepDefinitions(private val serverHttpClient: ServerHttpClient) : AnnotationSpec() {

    @Given("an authenticated user in the system")
    fun `an authenticated user in the system`() {
        // Write code here that turns the phrase above into concrete actions
        println("The user is authenticated")
    }
}
