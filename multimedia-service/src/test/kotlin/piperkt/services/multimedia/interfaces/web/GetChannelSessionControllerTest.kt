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
import piperkt.services.multimedia.domain.server.Channel
import piperkt.services.multimedia.domain.server.ChannelId
import piperkt.services.multimedia.domain.server.Server
import piperkt.services.multimedia.domain.server.ServerRepository
import piperkt.services.multimedia.domain.session.SessionFactory
import piperkt.services.multimedia.domain.session.SessionRepository
import piperkt.services.multimedia.interfaces.authOf

@Client("/")
interface GetChannelSessionControllerClient {

    @Get("/servers/{serverId}/channels/{channelId}/session")
    fun get(
        @Header(HttpHeaders.AUTHORIZATION) authorization: String,
        @PathVariable serverId: String,
        @PathVariable channelId: String,
    ): HttpResponse<GetChannelSessionController.Response>
}

class GetChannelSessionControllerTest(
    private val serverRepository: ServerRepository,
    private val sessionRepository: SessionRepository,
    private val client: GetChannelSessionControllerClient,
) :
    IntegrationTest.FunSpec({
        val users = setOf(john().id, jane().id)
        val server = Server(members = users)
        val session = SessionFactory.fromAllowedParticipants(users)
        val channel = Channel(id = ChannelId(), sessionId = session.id)
        server.addChannel(channel)

        beforeEach {
            sessionRepository.save(session)
            serverRepository.save(server)
        }

        afterEach {
            serverRepository.deleteAll()
            sessionRepository.deleteAll()
        }

        test("get channel session") {
            val response = client.get(authOf(john().username), server.id.value, channel.id.value)
            response.status shouldBe HttpStatus.OK
            response.body().sessionId shouldBe session.id.value
        }

        test("get channel session with invalid server") {
            val response = client.get(authOf(john().username), "invalid", channel.id.value)
            response.status shouldBe HttpStatus.NOT_FOUND
        }

        test("get channel session with invalid channel") {
            val response = client.get(authOf(john().username), server.id.value, "invalid")
            response.status shouldBe HttpStatus.NOT_FOUND
        }

        test("get channel session with invalid user") {
            assertThrows<HttpClientResponseException> {
                    client.get(authOf("invalid"), server.id.value, channel.id.value)
                }
                .let { it.status shouldBe HttpStatus.FORBIDDEN }
        }
    })
