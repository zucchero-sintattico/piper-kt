package piperkt.services.servers.interfaces.web.server

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.assertThrows
import piperkt.services.servers.interfaces.web.api.interactions.ServerApi

@MicronautTest
class ServerHttpControllerTest : AnnotationSpec() {

    @Inject
    lateinit var client: ServerControllerClient

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
        val updateResponse =
            client.updateServer(
                serverId = createResponse.body().serverId,
                request =
                ServerApi.UpdateServerApi.Request(
                    name = "newName",
                    description = "newDescription"
                )
            )
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
            .let { it.status shouldBe HttpStatus.FORBIDDEN }
    }

    @Test
    fun `should delete server`() {
        val serverId =
            client
                .createServer(
                    ServerApi.CreateServerApi.Request(name = "name", description = "description")
                )
                .body()
                .serverId
        val response = client.deleteServer(serverId)
        response.status() shouldBe HttpStatus.OK
    }

    @Test
    fun `should not delete server when not found`() {
        val response = client.deleteServer("serverId")
        response.status() shouldBe HttpStatus.NOT_FOUND
    }

    @Test
    fun `should not delete server when not admin`() {
        val serverId =
            client
                .createServer(
                    ServerApi.CreateServerApi.Request(name = "name", description = "description")
                )
                .body()
                .serverId
        assertThrows<HttpClientResponseException> {
            client.deleteServer(serverId = serverId, authorization = authOf("anotherUser"))
        }
            .let { it.status shouldBe HttpStatus.FORBIDDEN }
    }

    @Test
    fun `should add user to server`() {
        val serverId =
            client
                .createServer(
                    ServerApi.CreateServerApi.Request(name = "name", description = "description")
                )
                .body()
                .serverId
        val response =
            client.addUserToServer(serverId = serverId, authorization = authOf("otherMember"))
        response.status() shouldBe HttpStatus.OK
    }

    @Test
    fun `should not add user to server when not found`() {
        val response =
            client.addUserToServer(serverId = "serverId", authorization = authOf("otherMember"))
        response.status() shouldBe HttpStatus.NOT_FOUND
    }

    @Test
    fun `should remove user from server`() {
        val serverId =
            client
                .createServer(
                    ServerApi.CreateServerApi.Request(name = "name", description = "description")
                )
                .body()
                .serverId
        client.addUserToServer(serverId = serverId, authorization = authOf("otherMember"))
        val response =
            client.removeUserFromServer(serverId = serverId, authorization = authOf("otherMember"))
        response.status() shouldBe HttpStatus.OK
    }

    @Test
    fun `should not remove user from server when server not found`() {
        val response =
            client.removeUserFromServer(
                serverId = "serverId",
                authorization = authOf("otherMember")
            )
        response.status() shouldBe HttpStatus.NOT_FOUND
    }

    @Test
    fun `should not remove user from server when user not found`() {
        val serverId =
            client
                .createServer(
                    ServerApi.CreateServerApi.Request(name = "name", description = "description")
                )
                .body()
                .serverId
        val response =
            client.removeUserFromServer(serverId = serverId, authorization = authOf("otherMember"))
        response.status() shouldBe HttpStatus.NOT_FOUND
    }

    @Test
    fun `should kick user from server`() {
        val serverId =
            client
                .createServer(
                    ServerApi.CreateServerApi.Request(name = "name", description = "description")
                )
                .body()
                .serverId
        client.addUserToServer(serverId = serverId, authorization = authOf("otherMember"))
        val response =
            client.kickUserFromServer(
                serverId = serverId,
                username = "otherMember",
            )
        response.status() shouldBe HttpStatus.OK
    }

    @Test
    fun `should not kick user from server when server not found`() {
        val response = client.kickUserFromServer("serverId", "otherMember")
        response.status() shouldBe HttpStatus.NOT_FOUND
    }

    @Test
    fun `should not kick user from server when user not found`() {
        val serverId =
            client
                .createServer(
                    ServerApi.CreateServerApi.Request(name = "name", description = "description")
                )
                .body()
                .serverId
        val response = client.kickUserFromServer(serverId, "otherMember")
        response.status() shouldBe HttpStatus.NOT_FOUND
    }

    @Test
    fun `should not kick user from server when not admin`() {
        val serverId =
            client
                .createServer(
                    ServerApi.CreateServerApi.Request(name = "name", description = "description")
                )
                .body()
                .serverId
        client.addUserToServer(serverId = serverId, authorization = authOf("otherMember"))
        assertThrows<HttpClientResponseException> {
            client.kickUserFromServer(serverId, "otherMember", authOf("anotherUser"))
        }
            .let { it.status shouldBe HttpStatus.FORBIDDEN }
    }

    // QUERIES

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
