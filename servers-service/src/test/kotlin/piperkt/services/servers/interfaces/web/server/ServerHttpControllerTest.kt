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
    fun `should return a 200 status code when creating a server`() {
        client
            .createServer(
                request =
                    ServerApi.CreateServerApi.Request(name = "name", description = "description")
            )
            .status() shouldBe HttpStatus.OK
    }

    @Test
    fun `should return a 200 status code when an admin tries to update a server`() {
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
    fun `should return a 404 status code when updating a non-existing server`() {
        val response =
            client.updateServer(
                "serverId",
                ServerApi.UpdateServerApi.Request(name = "newName", description = "newDescription")
            )
        response.status() shouldBe HttpStatus.NOT_FOUND
    }

    @Test
    fun `should return a 403 status code when a non-admin tries to update a server`() {
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
    fun `should return a 200 status code when an admin tries to delete a server`() {
        val serverId = createSimpleServerAndGetId()

        val response = client.deleteServer(serverId)
        response.status() shouldBe HttpStatus.OK
    }

    @Test
    fun `should return a 404 status code when deleting a non-existing server`() {
        val response = client.deleteServer("serverId")
        response.status() shouldBe HttpStatus.NOT_FOUND
    }

    @Test
    fun `should return a 403 status code when a non-admin tries to delete a server`() {
        val serverId = createSimpleServerAndGetId()
        assertThrows<HttpClientResponseException> {
                client.deleteServer(serverId = serverId, authorization = authOf("anotherUser"))
            }
            .let { it.status shouldBe HttpStatus.FORBIDDEN }
    }

    @Test
    fun `should return a 200 status code when an admin tries to add a user to server`() {
        val serverId = createSimpleServerAndGetId()
        val response =
            client.addUserToServer(serverId = serverId, authorization = authOf("otherMember"))
        response.status() shouldBe HttpStatus.OK
    }

    @Test
    fun `should return a 404 status code when adding a user to a non-existing server`() {
        val response =
            client.addUserToServer(serverId = "serverId", authorization = authOf("otherMember"))
        response.status() shouldBe HttpStatus.NOT_FOUND
    }

    @Test
    fun `should return a 200 status code when an user leaves a server`() {
        val serverId = createSimpleServerAndGetId()
        client.addUserToServer(serverId = serverId, authorization = authOf("otherMember"))
        val response =
            client.removeUserFromServer(serverId = serverId, authorization = authOf("otherMember"))
        response.status() shouldBe HttpStatus.OK
    }

    @Test
    fun `should return a 404 status code when removing a user from a non-existing server`() {
        val response =
            client.removeUserFromServer(
                serverId = "serverId",
                authorization = authOf("otherMember")
            )
        response.status() shouldBe HttpStatus.NOT_FOUND
    }

    @Test
    fun `should return a 404 status code when removing a non-existing user from a server`() {
        val serverId = createSimpleServerAndGetId()
        val response =
            client.removeUserFromServer(serverId = serverId, authorization = authOf("otherMember"))
        response.status() shouldBe HttpStatus.NOT_FOUND
    }

    @Test
    fun `should return a 422 status code when removing the owner from a server`() {
        val serverId = createSimpleServerAndGetId()
        assertThrows<HttpClientResponseException> {
                client.removeUserFromServer(serverId, authOf("user"))
            }
            .let { it.status shouldBe HttpStatus.UNPROCESSABLE_ENTITY }
    }

    @Test
    fun `should return a 200 status code when an admin tries to kick a user from a server`() {
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
    fun `should return a 404 status code when kicking a user from a non-existing server`() {
        val response = client.kickUserFromServer("serverId", "otherMember")
        response.status() shouldBe HttpStatus.NOT_FOUND
    }

    @Test
    fun `should return a 404 status code when kicking a non-existing user from a server`() {
        val serverId = createSimpleServerAndGetId()
        val response = client.kickUserFromServer(serverId, "otherMember")
        response.status() shouldBe HttpStatus.NOT_FOUND
    }

    @Test
    fun `should return a 403 status code when a non-admin tries to kick a user from a server`() {
        val serverId = createSimpleServerAndGetId()
        client.addUserToServer(serverId = serverId, authorization = authOf("otherMember"))
        assertThrows<HttpClientResponseException> {
                client.kickUserFromServer(serverId, "otherMember", authOf("anotherUser"))
            }
            .let { it.status shouldBe HttpStatus.FORBIDDEN }
    }

    // QUERIES

    @Test
    fun `should return a 200 status code when getting servers from a user`() {
        val response = client.getServersFromUser(authOf("userWithNoServers"))
        response.status() shouldBe HttpStatus.OK
        response.body() shouldBe ServerApi.GetServersFromUserApi.Response(servers = emptyList())
    }

    @Test
    fun `should return a 200 status code when getting users from a server`() {
        val response = client.getServerUsers("serverId")
        response.status() shouldBe HttpStatus.NOT_FOUND
    }
}
