package piperkt.services.servers.interfaces.web

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.micronaut.http.HttpHeaders
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import jakarta.inject.Inject
import piperkt.services.servers.interfaces.web.api.servers.ServerApi

@Client("/servers")
interface ServerControllerClient {

    @Get("/")
    fun getServersFromUser(@Header(HttpHeaders.AUTHORIZATION) authorization: String = authOf("user")):
            HttpResponse<ServerApi.GetServersFromUserApi.Response>


}

fun authOf(username: String): String {
    val req = HttpRequest.GET<Any>("/").basicAuth(username, "")
    return req.headers.get(HttpHeaders.AUTHORIZATION)!!
}


@MicronautTest
class ServerHttpControllerTest : AnnotationSpec() {

    @Inject
    lateinit var client: ServerControllerClient

    @Test
    fun `should return servers from user`() {
        val response = client.getServersFromUser()
        response.status() shouldBe HttpStatus.OK
        response.body() shouldBe ServerApi.GetServersFromUserApi.Response(
            servers = emptyList()
        )
    }
}
