package piperkt.services.servers.interfaces.web.channel

import base.IntegrationTest
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException
import org.junit.jupiter.api.assertThrows
import piperkt.services.servers.interfaces.web.api.interactions.ChannelApi
import piperkt.services.servers.interfaces.web.api.interactions.ServerApi
import piperkt.services.servers.interfaces.web.authOf

class ChannelHttpControllerTest(private var client: ChannelHttpClient) : IntegrationTest() {

    private lateinit var basicServerId: String

    @BeforeEach
    fun setup() {
        basicServerId =
            client
                .createServer(
                    ServerApi.CreateServerApi.Request(name = "name", description = "description")
                )
                .body()
                .serverId
    }

    private fun createChannelAndGetItsId(): String {
        return client
            .createChannel(
                serverId = basicServerId,
                request =
                    ChannelApi.CreateChannelApi.Request(
                        name = "name",
                        description = "description",
                        channelType = "TEXT"
                    )
            )
            .body()
            .channelId
    }

    @Test
    fun `should return a 200 status code when creating a channel`() {
        val response =
            client.createChannel(
                serverId = basicServerId,
                request =
                    ChannelApi.CreateChannelApi.Request(
                        name = "name",
                        description = "description",
                        channelType = "TEXT"
                    )
            )
        response.status() shouldBe HttpStatus.OK
    }

    @Test
    fun `should return a 200 status code when an admin tries to update a channel`() {
        val updateResponse =
            createChannelAndGetItsId().let { channelId ->
                client.updateChannel(
                    serverId = basicServerId,
                    channelId = channelId,
                    request =
                        ChannelApi.UpdateChannelApi.Request(
                            name = "newName",
                            description = "newDescription"
                        )
                )
            }
        updateResponse.status() shouldBe HttpStatus.OK
    }

    @Test
    fun `should return a 404 status code when updating a non-existing channel`() {
        val response =
            client.updateChannel(
                serverId = basicServerId,
                channelId = "channelId",
                request =
                    ChannelApi.UpdateChannelApi.Request(
                        name = "newName",
                        description = "newDescription"
                    )
            )
        response.status() shouldBe HttpStatus.NOT_FOUND
    }

    @Test
    fun `should return a 403 status code when a non-admin tries to update a channel`() {
        val channelId = createChannelAndGetItsId()
        shouldThrow<HttpClientResponseException> {
                client.updateChannel(
                    serverId = basicServerId,
                    channelId = channelId,
                    request =
                        ChannelApi.UpdateChannelApi.Request(
                            name = "newName",
                            description = "newDescription"
                        ),
                    authorization = authOf("anotherUser")
                )
            }
            .also { it.status shouldBe HttpStatus.FORBIDDEN }
    }

    @Test
    fun `should return a 200 status code when an admin tries to delete a channel`() {
        createChannelAndGetItsId().let { channelId ->
            val response = client.deleteChannel(serverId = basicServerId, channelId = channelId)
            response.status() shouldBe HttpStatus.OK
        }
    }

    @Test
    fun `should return a 404 status code when deleting a non-existing channel`() {
        val response = client.deleteChannel(serverId = basicServerId, channelId = "channelId")
        response.status() shouldBe HttpStatus.NOT_FOUND
    }

    @Test
    fun `should return a 403 status code when a non-admin tries to delete a channel`() {
        createChannelAndGetItsId().let { channelId ->
            shouldThrow<HttpClientResponseException> {
                    client.deleteChannel(
                        serverId = basicServerId,
                        channelId = channelId,
                        authorization = authOf("anotherUser")
                    )
                }
                .let { it.status shouldBe HttpStatus.FORBIDDEN }
        }
    }

    @Test
    fun `should return a 200 status code when getting channels from server`() {
        val response =
            createChannelAndGetItsId().let {
                client.getChannelsFromServer(serverId = basicServerId)
            }
        response.status() shouldBe HttpStatus.OK
    }

    @Test
    fun `should return a 404 status code when getting channels from non-existing server`() {
        val response = client.getChannelsFromServer(serverId = "serverId")
        response.status() shouldBe HttpStatus.NOT_FOUND
    }

    @Test
    fun `should return a 403 status code when a user not in the server tries to get channels of the server`() {
        assertThrows<HttpClientResponseException> {
                createChannelAndGetItsId().let {
                    client.getChannelsFromServer(
                        serverId = basicServerId,
                        authorization = authOf("anotherUser")
                    )
                }
            }
            .let { it.status shouldBe HttpStatus.FORBIDDEN }
    }

    @Test
    fun `should return a 200 status code when getting channel messages`() {
        val response =
            createChannelAndGetItsId().let {
                client.getChannelMessages(
                    serverId = basicServerId,
                    channelId = it,
                    from = 0,
                    limit = 10
                )
            }
        response.status() shouldBe HttpStatus.OK
    }

    @Test
    fun `should return a 404 status code when getting channel messages from non-existing channel`() {
        val response =
            client.getChannelMessages(
                serverId = basicServerId,
                channelId = "channelId",
                from = 0,
                limit = 10
            )
        response.status() shouldBe HttpStatus.NOT_FOUND
    }

    @Test
    fun `should return a 403 status code when a user not in the server tries to get channel messages`() {
        assertThrows<HttpClientResponseException> {
                createChannelAndGetItsId().let { channelId ->
                    client.getChannelMessages(
                        serverId = basicServerId,
                        channelId = channelId,
                        from = 0,
                        limit = 10,
                        authorization = authOf("anotherUser")
                    )
                }
            }
            .let { it.status shouldBe HttpStatus.FORBIDDEN }
    }

    @Test
    fun `should return a 200 status code when sending message to channel`() {
        val response =
            createChannelAndGetItsId().let { channelId ->
                client.sendMessageToChannel(
                    serverId = basicServerId,
                    channelId = channelId,
                    request = ChannelApi.SendMessageToChannelApi.Request(content = "message")
                )
            }
        response.status() shouldBe HttpStatus.OK
    }

    @Test
    fun `should return a 404 status code when sending message to non-existing channel`() {
        val response =
            client.sendMessageToChannel(
                serverId = basicServerId,
                channelId = "channelId",
                request = ChannelApi.SendMessageToChannelApi.Request(content = "message")
            )
        response.status() shouldBe HttpStatus.NOT_FOUND
    }

    @Test
    fun `should return a 403 status code when a user not in the server tries to send message to channel`() {
        assertThrows<HttpClientResponseException> {
                createChannelAndGetItsId().let { channelId ->
                    client.sendMessageToChannel(
                        serverId = basicServerId,
                        channelId = channelId,
                        request = ChannelApi.SendMessageToChannelApi.Request(content = "message"),
                        authorization = authOf("anotherUser")
                    )
                }
            }
            .let { it.status shouldBe HttpStatus.FORBIDDEN }
    }
}
