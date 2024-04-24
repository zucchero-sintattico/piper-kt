package piperkt.services.servers.interfaces.web

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.micronaut.http.HttpHeaders
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.retry.annotation.Retryable
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.assertThrows
import piperkt.services.servers.interfaces.web.api.servers.ServerApi

@Client("/servers")
@Retryable
interface ServerControllerClient {

    @Get("/")
    fun getServersFromUser(
        @Header(HttpHeaders.AUTHORIZATION) authorization: String = authOf("user")
    ): HttpResponse<ServerApi.GetServersFromUserApi.Response>

    @Get("/{serverId}/users")
    fun getServerUsers(
        @PathVariable serverId: String,
        @Header(HttpHeaders.AUTHORIZATION) authorization: String = authOf("user")
    ): HttpResponse<ServerApi.GetServerUsersApi.Response>

    @Post("/")
    fun createServer(
        @Body request: ServerApi.CreateServerApi.Request,
        @Header(HttpHeaders.AUTHORIZATION) authorization: String = authOf("user")
    ): HttpResponse<ServerApi.CreateServerApi.Response>

    @Put("/{serverId}/")
    fun updateServer(
        @PathVariable serverId: String,
        @Body request: ServerApi.UpdateServerApi.Request,
        @Header(HttpHeaders.AUTHORIZATION) authorization: String = authOf("user")
    ): HttpResponse<Any>
}

fun authOf(username: String): String {
    val req = HttpRequest.GET<Any>("/").basicAuth(username, "")
    return req.headers.get(HttpHeaders.AUTHORIZATION)!!
}

@MicronautTest
class ServerHttpControllerTest : AnnotationSpec() {

    @Inject lateinit var client: ServerControllerClient

    @Test
    fun `should create server`() {
        val response =
            client.createServer(
                ServerApi.CreateServerApi.Request(name = "name", description = "description")
            )
        response.status() shouldBe HttpStatus.OK
    }

    @Test
    fun `should correctly update server`() {
        // Step 1: Create a server
        val createResponse =
            client.createServer(
                ServerApi.CreateServerApi.Request(name = "name", description = "description")
            )
        createResponse.status() shouldBe HttpStatus.OK

        // Step 2: Update the server
        val updateResponse =
            client.updateServer(
                serverId = createResponse.body().serverId,
                request =
                    ServerApi.UpdateServerApi.Request(
                        name = "newName",
                        description = "newDescription"
                    )
            )

        // Step 3: Verify the response
        updateResponse.status() shouldBe HttpStatus.OK
    }

    @Test
    fun `should not update server when not found`() {
        val response =
            client.updateServer(
                "serverId",
                ServerApi.UpdateServerApi.Request(name = "newName", description = "newDescription")
            )
        response.status() shouldBe HttpStatus.NOT_FOUND
    }

    @Test
    fun `should not update server when not admin`() {
        val serverId =
            client
                .createServer(
                    ServerApi.CreateServerApi.Request(name = "name", description = "description")
                )
                .body()
                .serverId
        assertThrows<HttpClientResponseException> {
                client.updateServer(
                    serverId = serverId,
                    request =
                        ServerApi.UpdateServerApi.Request(
                            name = "newName",
                            description = "newDescription"
                        ),
                    authorization = authOf("anotherUser")
                )
            }
            .let { it.status shouldBe HttpStatus.UNAUTHORIZED }
    }

    @Test
    fun `should return servers from user`() {
        val response = client.getServersFromUser(authOf("userWithNoServers"))
        response.status() shouldBe HttpStatus.OK
        response.body() shouldBe ServerApi.GetServersFromUserApi.Response(servers = emptyList())
    }

    @Test
    fun `should return 404 when server not found`() {
        val response = client.getServerUsers("serverId")
        response.status() shouldBe HttpStatus.NOT_FOUND
    }
}
