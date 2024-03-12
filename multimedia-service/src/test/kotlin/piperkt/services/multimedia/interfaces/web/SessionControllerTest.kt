package piperkt.services.multimedia.interfaces.web

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import piperkt.services.multimedia.interfaces.web.api.GetUsersInSessionResponse

@Client("/sessions") interface SessionClient : SessionController

@MicronautTest
class SessionControllerTest(private val sessionClient: SessionClient) :
    FunSpec({
        val fakeSessionId = "000000000000000000000000"
        test("should get users in session") {
            val response = sessionClient.getUsers(fakeSessionId)
            response.body() shouldBe GetUsersInSessionResponse(emptySet())
        }
    })
