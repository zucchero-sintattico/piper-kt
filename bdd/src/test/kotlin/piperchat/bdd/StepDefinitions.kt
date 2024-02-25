package piperchat.bdd

import io.cucumber.java.en.Given
import io.kotest.core.spec.style.AnnotationSpec

class StepDefinitions : AnnotationSpec() {
    @Given("the user is the owner of a server")
    fun theUserIsTheOwnerOfAServer() {
        print("todo")
    }
}
