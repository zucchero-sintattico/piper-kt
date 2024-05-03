package piperkt.services.servers.interfaces.web.server

import base.IntegrationTest
import io.kotest.matchers.shouldBe
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException
import org.junit.jupiter.api.assertThrows
import piperkt.services.servers.application.ServerRepository
import piperkt.services.servers.interfaces.web.api.interactions.ServerApi
import piperkt.services.servers.interfaces.web.authOf

class ServerHttpControllerTest(
    private var client: ServerHttpClient,
    private var serverRepository: ServerRepository
) : IntegrationTest() {

    @BeforeEach
    fun setup() {
        serverRepository.deleteAll()
    }

    private fun createSimpleServerAndGetId(): String {
        return client
            .createServer(
                request =
                    ServerApi.CreateServerApi.Request(name = "name", description = "description")
            )
            .body()
            .serverId
    }

    @Test
    fun `should create server`() {
        client
            .createServer(
                request =
                    ServerApi.CreateServerApi.Request(name = "name", description = "description")
            )
            .status() shouldBe HttpStatus.OK
    }

    @Test
    fun `should correctly update server`() {
        createSimpleServerAndGetId().let { serverId ->
            val updateResponse =
                client.updateServer(
                    serverId = serverId,
                    request =
                        ServerApi.UpdateServerApi.Request(
                            name = "newName",
                            description = "newDescription"
                        )
                )
            updateResponse.status() shouldBe HttpStatus.OK
        }
    }

    @Test
    fun `should return 404 when trying to update a non-existing server`() {
        val response =
            client.updateServer(
                "serverId",
                ServerApi.UpdateServerApi.Request(name = "newName", description = "newDescription")
            )
        response.status() shouldBe HttpStatus.NOT_FOUND
    }

    @Test
    fun `should return 403 when a non-admin tries to update a server`() {
        val serverId = createSimpleServerAndGetId()

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
        val serverId = createSimpleServerAndGetId()

        val response = client.deleteServer(serverId)
        response.status() shouldBe HttpStatus.OK
    }

    @Test
    fun `should return 404 when delete a non-existing server`() {
        val response = client.deleteServer("serverId")
        response.status() shouldBe HttpStatus.NOT_FOUND
    }

    @Test
    fun `should return 403 when a non-admin tries to delete a server`() {
        val serverId = createSimpleServerAndGetId()
        assertThrows<HttpClientResponseException> {
                client.deleteServer(serverId = serverId, authorization = authOf("anotherUser"))
            }
            .let { it.status shouldBe HttpStatus.FORBIDDEN }
    }

    @Test
    fun `should add user to server`() {
        val serverId = createSimpleServerAndGetId()
        val response =
            client.addUserToServer(serverId = serverId, authorization = authOf("otherMember"))
        response.status() shouldBe HttpStatus.OK
    }

    @Test
    fun `should return 404 when trying to add user in a non-existing server`() {
        val response =
            client.addUserToServer(serverId = "serverId", authorization = authOf("otherMember"))
        response.status() shouldBe HttpStatus.NOT_FOUND
    }

    @Test
    fun `should remove user from server`() {
        val serverId = createSimpleServerAndGetId()
        client.addUserToServer(serverId = serverId, authorization = authOf("otherMember"))
        val response =
            client.removeUserFromServer(serverId = serverId, authorization = authOf("otherMember"))
        response.status() shouldBe HttpStatus.OK
    }

    @Test
    fun `should return 404 when trying to remove a user from a non-existing server`() {
        val response =
            client.removeUserFromServer(
                serverId = "serverId",
                authorization = authOf("otherMember")
            )
        response.status() shouldBe HttpStatus.NOT_FOUND
    }

    @Test
    fun `should return 404 when trying to remove non-existing user from a server`() {
        val serverId = createSimpleServerAndGetId()
        val response =
            client.removeUserFromServer(serverId = serverId, authorization = authOf("otherMember"))
        response.status() shouldBe HttpStatus.NOT_FOUND
    }

    @Test
    fun `should kick user from server`() {
        val serverId = createSimpleServerAndGetId()
        client.addUserToServer(serverId = serverId, authorization = authOf("otherMember"))
        val response =
            client.kickUserFromServer(
                serverId = serverId,
                username = "otherMember",
            )
        response.status() shouldBe HttpStatus.OK
    }

    @Test
    fun `should return 404 when trying to kick a user from a non-existing server`() {
        val response = client.kickUserFromServer("serverId", "otherMember")
        response.status() shouldBe HttpStatus.NOT_FOUND
    }

    @Test
    fun `should return 404 when trying to kick non-existing user from a server`() {
        val serverId = createSimpleServerAndGetId()
        val response = client.kickUserFromServer(serverId, "otherMember")
        response.status() shouldBe HttpStatus.NOT_FOUND
    }

    @Test
    fun `should return 403 when a non-admin tries to kick a user from a server`() {
        val serverId = createSimpleServerAndGetId()
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
    fun `should return 404 when trying to get a non-existing server info`() {
        val response = client.getServerUsers("serverId")
        response.status() shouldBe HttpStatus.NOT_FOUND
    }
}
