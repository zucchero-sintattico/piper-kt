package integration

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import piperkt.services.multimedia.interfaces.web.api.GetSessionParticipantsApi

@Client("/")
interface GetUserInSessionClient {
    @Get("/sessions/{sessionId}/users")
    fun get(@PathVariable sessionId: String): HttpResponse<GetSessionParticipantsApi.Response>
}

@MicronautTest
class GetUserInSessionTest(private val getUserInSessionClient: GetUserInSessionClient) :
    FunSpec({
        test("should return Not Found when session does not exist") {
            val sessionId = "000000000000000000000000"
            val exception = getUserInSessionClient.get(sessionId)
            exception.status.code shouldBe 404
        }
    })
