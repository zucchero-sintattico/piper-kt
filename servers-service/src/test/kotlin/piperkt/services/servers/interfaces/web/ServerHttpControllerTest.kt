package piperkt.services.servers.interfaces.web

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import piperkt.services.servers.interfaces.web.api.responses.GetServerUsersHttpResponse

@Client("/servers")
interface ServerClient {
    @Get("/{serverId}/users")
    fun getUserInServer(@PathVariable serverId: String): HttpResponse<GetServerUsersHttpResponse>
}

@MicronautTest
class SessionControllerTest(private val client: ServerClient) :
    FunSpec({
        val fakeServerId = "000000000000000000000000"

        test("getUsers with a fake id should return a 404") {
            val response = client.getUserInServer(fakeServerId)
            response.status shouldBe HttpStatus.NOT_FOUND
        }
    })
