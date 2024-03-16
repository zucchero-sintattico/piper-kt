package piperkt.services.multimedia.interfaces.web

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import piperkt.services.multimedia.interfaces.web.api.GetUsersInSessionApi

@Client("/sessions")
interface SessionClient {
    @Get("/{sessionId}/users")
    fun getUsersInSession(
        @PathVariable sessionId: String
    ): HttpResponse<GetUsersInSessionApi.Response>
}

@MicronautTest
class SessionControllerTest(private val client: SessionClient) :
    FunSpec({
        val fakeSessionId = "000000000000000000000000"

        test("getUsers with a fake id should return a 404") {
            val response = client.getUsersInSession(fakeSessionId)
            response.status shouldBe HttpStatus.NOT_FOUND
        }
    })
