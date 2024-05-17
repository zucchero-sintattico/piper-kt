package piperkt.services.multimedia.interfaces.web

import base.IntegrationTest
import data.UsersData.jane
import data.UsersData.john
import io.kotest.matchers.shouldBe
import io.micronaut.http.HttpHeaders
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import org.junit.jupiter.api.assertThrows
import piperkt.services.multimedia.domain.session.SessionFactory
import piperkt.services.multimedia.domain.session.SessionRepository
import piperkt.services.multimedia.interfaces.authOf
import piperkt.services.multimedia.interfaces.web.api.GetSessionParticipantsApi

@Client("/")
interface GetSessionParticipantsControllerClient {

    @Get("/sessions/{sessionId}/users")
    fun getParticipants(
        @Header(HttpHeaders.AUTHORIZATION) authorization: String,
        @PathVariable sessionId: String,
    ): HttpResponse<GetSessionParticipantsApi.Response>
}

class GetSessionParticipantsControllerTest(
    private val sessionRepository: SessionRepository,
    private val client: GetSessionParticipantsControllerClient,
) :
    IntegrationTest.FunSpec({
        val session = SessionFactory.fromAllowedParticipants(setOf(john().id))

        beforeEach { sessionRepository.save(session) }

        afterEach { sessionRepository.deleteAll() }

        test("should retrieve the participants of a session") {
            val response = client.getParticipants(authOf(john().username), session.id.value)
            response.body().users shouldBe setOf(john().username)
        }

        test("should return an error when the session does not exist") {
            val response = client.getParticipants(authOf(john().username), "invalid-session-id")
            response.status.code shouldBe 404
        }

        test("should return an error when the user is not allowed") {
            assertThrows<HttpClientResponseException> {
                    client.getParticipants(authOf(jane().username), session.id.value)
                }
                .let { it.status shouldBe HttpStatus.FORBIDDEN }
        }
    })
