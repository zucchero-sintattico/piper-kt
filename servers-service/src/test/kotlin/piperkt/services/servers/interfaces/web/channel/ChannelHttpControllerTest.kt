package piperkt.services.servers.interfaces.web.channel

import base.IntegrationTest
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException
import jakarta.inject.Inject
import org.junit.jupiter.api.assertThrows
import piperkt.services.servers.interfaces.web.api.interactions.ChannelApi
import piperkt.services.servers.interfaces.web.api.interactions.ServerApi
import piperkt.services.servers.interfaces.web.authOf

class ChannelHttpControllerTest : IntegrationTest() {

    @Inject lateinit var client: ChannelHttpClient

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
                        type = "TEXT"
                    )
            )
            .body()
            .channelId
    }

    @Test
    fun `should create channel`() {
        val response =
            client.createChannel(
                serverId = basicServerId,
                request =
                    ChannelApi.CreateChannelApi.Request(
                        name = "name",
                        description = "description",
                        type = "TEXT"
                    )
            )
        response.status() shouldBe HttpStatus.OK
    }

    @Test
    fun `should correctly update channel`() {
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
    fun `should not update channel when not found`() {
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
    fun `should not update channel when not admin`() {
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
    fun `should delete channel`() {
        createChannelAndGetItsId().let { channelId ->
            val response = client.deleteChannel(serverId = basicServerId, channelId = channelId)
            response.status() shouldBe HttpStatus.OK
        }
    }

    @Test
    fun `should not delete channel when not found`() {
        val response = client.deleteChannel(serverId = basicServerId, channelId = "channelId")
        response.status() shouldBe HttpStatus.NOT_FOUND
    }

    @Test
    fun `should not delete channel when not admin`() {
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
    fun `should get channels from server`() {
        val response =
            createChannelAndGetItsId().let {
                client.getChannelsFromServer(serverId = basicServerId)
            }
        response.status() shouldBe HttpStatus.OK
    }

    @Test
    fun `should not get channels from server when not found`() {
        val response = client.getChannelsFromServer(serverId = "serverId")
        response.status() shouldBe HttpStatus.NOT_FOUND
    }

    @Test
    fun `should not get channels from server when user not in server`() {
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
    fun `should get channel messages`() {
        val response =
            createChannelAndGetItsId().let {
                client.getChannelMessages(
                    serverId = basicServerId,
                    channelId = it,
                    from = 0,
                    to = 10
                )
            }
        response.status() shouldBe HttpStatus.OK
    }

    @Test
    fun `should not get channel messages when channel not found`() {
        val response =
            client.getChannelMessages(
                serverId = basicServerId,
                channelId = "channelId",
                from = 0,
                to = 10
            )
        response.status() shouldBe HttpStatus.NOT_FOUND
    }

    @Test
    fun `should not get channel messages when user not in server`() {
        assertThrows<HttpClientResponseException> {
                createChannelAndGetItsId().let { channelId ->
                    client.getChannelMessages(
                        serverId = basicServerId,
                        channelId = channelId,
                        from = 0,
                        to = 10,
                        authorization = authOf("anotherUser")
                    )
                }
            }
            .let { it.status shouldBe HttpStatus.FORBIDDEN }
    }

    @Test
    fun `should send message to channel`() {
        val response =
            createChannelAndGetItsId().let { channelId ->
                client.sendMessageToChannel(
                    serverId = basicServerId,
                    channelId = channelId,
                    request = ChannelApi.SendMessageToChannelApi.Request(message = "message")
                )
            }
        response.status() shouldBe HttpStatus.OK
    }

    @Test
    fun `should not send message to channel when not found`() {
        val response =
            client.sendMessageToChannel(
                serverId = basicServerId,
                channelId = "channelId",
                request = ChannelApi.SendMessageToChannelApi.Request(message = "message")
            )
        response.status() shouldBe HttpStatus.NOT_FOUND
    }
}
