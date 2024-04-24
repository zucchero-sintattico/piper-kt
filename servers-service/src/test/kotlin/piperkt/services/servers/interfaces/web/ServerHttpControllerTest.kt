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
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client
import io.micronaut.retry.annotation.Retryable
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import jakarta.inject.Inject
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
        serverId: String,
        @Header(HttpHeaders.AUTHORIZATION) authorization: String = authOf("user")
    ): HttpResponse<ServerApi.GetServerUsersApi.Response>

    @Post("/")
    fun createServer(
        @Body request: ServerApi.CreateServerApi.Request,
        @Header(HttpHeaders.AUTHORIZATION) authorization: String = authOf("user")
    ): HttpResponse<ServerApi.CreateServerApi.Response>
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
    fun `should return servers from user`() {
        val response = client.getServersFromUser(authOf("anotherUser"))
        response.status() shouldBe HttpStatus.OK
        response.body() shouldBe ServerApi.GetServersFromUserApi.Response(servers = emptyList())
    }

    @Test
    fun `should return 404 when server not found`() {
        val response = client.getServerUsers("serverId")
        response.status() shouldBe HttpStatus.NOT_FOUND
    }
}
