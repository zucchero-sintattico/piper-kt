package integration

import base.IntegrationTest
import data.UsersData.john
import io.kotest.matchers.shouldBe
import io.micronaut.http.HttpHeaders
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.client.annotation.Client

@Client("/")
interface GetSessionParticipantsClient {
    @Get("/sessions/{sessionId}/users")
    fun get(
        @Header(HttpHeaders.AUTHORIZATION) authorization: String,
        @PathVariable sessionId: String
    ): GetSessionParticipantsApi.Response
}

class GetSessionParticipantsTest(
    private val getSessionParticipantsClient: GetSessionParticipantsClient
) :
    IntegrationTest.FunSpec({
        test("should return Not Found when session does not exist") {
            val sessionId = "000000000000000000000000"
            val response = getSessionParticipantsClient.get(john().id.value, sessionId)
            response shouldBe GetSessionParticipantsApi.Errors.SessionNotFound(sessionId)
        }
    })
