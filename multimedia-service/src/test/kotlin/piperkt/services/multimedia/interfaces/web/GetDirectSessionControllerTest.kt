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
import piperkt.services.multimedia.domain.direct.Direct
import piperkt.services.multimedia.domain.direct.DirectId
import piperkt.services.multimedia.domain.direct.DirectRepository
import piperkt.services.multimedia.domain.session.SessionFactory
import piperkt.services.multimedia.domain.session.SessionRepository
import piperkt.services.multimedia.interfaces.authOf

@Client("/")
interface GetDirectSessionControllerClient {

    @Get("/users/{username}/session")
    fun get(
        @Header(HttpHeaders.AUTHORIZATION) authorization: String,
        @PathVariable username: String,
    ): HttpResponse<GetDirectSessionController.Response>
}

class GetDirectSessionControllerTest(
    private val directRepository: DirectRepository,
    private val sessionRepository: SessionRepository,
    private val client: GetDirectSessionControllerClient,
) :
    IntegrationTest.FunSpec({
        val users = setOf(john().id, jane().id)
        val session = SessionFactory.fromAllowedParticipants(users)
        val direct = Direct(id = DirectId(), users = users, sessionId = session.id)

        beforeEach {
            sessionRepository.save(session)
            directRepository.save(direct)
        }

        afterEach {
            directRepository.deleteAll()
            sessionRepository.deleteAll()
        }

        test("get direct session") {
            val response = client.get(authOf(john().username), jane().username)
            response.body().sessionId shouldBe session.id.value
        }

        test("get direct session with invalid user") {
            val response = client.get(authOf(john().username), "invalid")
            response.status shouldBe HttpStatus.NOT_FOUND
        }
    })
