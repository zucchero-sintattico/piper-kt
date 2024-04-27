package piperkt.services.servers.interfaces.web.channel

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.assertThrows
import piperkt.services.servers.interfaces.web.api.interactions.ChannelApi
import piperkt.services.servers.interfaces.web.api.interactions.ServerApi
import piperkt.services.servers.interfaces.web.authOf

@MicronautTest
class ChannelHttpControllerTest : AnnotationSpec() {

    @Inject lateinit var client: ChannelHttpClient

    private lateinit var serverId: String

    @BeforeEach
    fun setup() {
        serverId =
            client
                .createServer(
                    ServerApi.CreateServerApi.Request(name = "name", description = "description")
                )
                .body()
                .serverId
    }

    private fun createChannelAndGetId() =
        client
            .createChannel(
                serverId = serverId,
                request =
                    ChannelApi.CreateChannelApi.Request(
                        name = "name",
                        description = "description",
                        type = "TEXT"
                    )
            )
            .body()
            .channelId

    @Test
    fun `should create channel`() {
        val response =
            client.createChannel(
                serverId = serverId,
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
            createChannelAndGetId().let { channelId ->
                client.updateChannel(
                    serverId = serverId,
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
                serverId,
                "channelId",
                ChannelApi.UpdateChannelApi.Request(
                    name = "newName",
                    description = "newDescription"
                )
            )
        response.status() shouldBe HttpStatus.NOT_FOUND
    }

    @Test
    fun `should not update channel when not admin`() {
        val channelId = createChannelAndGetId()
        shouldThrow<HttpClientResponseException> {
                client.updateChannel(
                    serverId = serverId,
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
        createChannelAndGetId().let { channelId ->
            val response = client.deleteChannel(serverId, channelId)
            response.status() shouldBe HttpStatus.OK
        }
    }

    @Test
    fun `should not delete channel when not found`() {
        val response = client.deleteChannel(serverId, "channelId")
        response.status() shouldBe HttpStatus.NOT_FOUND
    }

    @Test
    fun `should not delete channel when not admin`() {
        createChannelAndGetId().let { channelId ->
            shouldThrow<HttpClientResponseException> {
                    client.deleteChannel(
                        serverId = serverId,
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
            createChannelAndGetId().let { client.getChannelsFromServer(serverId = serverId) }
        response.status() shouldBe HttpStatus.OK
    }

    @Test
    fun `should get channel messages`() {
        val response =
            createChannelAndGetId().let {
                client.getChannelMessages(serverId = serverId, channelId = it, from = 0, to = 10)
            }
        response.status() shouldBe HttpStatus.OK
    }

    @Test
    fun `should not get channel messages when channel not found`() {
        val response = client.getChannelMessages(serverId, "channelId", 0, 10)
        response.status() shouldBe HttpStatus.NOT_FOUND
    }

    @Test
    fun `should not get channel messages when user not in server`() {
        assertThrows<HttpClientResponseException> {
                createChannelAndGetId().let { channelId ->
                    client.getChannelMessages(
                        serverId = serverId,
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
            createChannelAndGetId().let { channelId ->
                client.sendMessageToChannel(
                    serverId = serverId,
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
                serverId,
                "channelId",
                ChannelApi.SendMessageToChannelApi.Request(message = "message")
            )
        response.status() shouldBe HttpStatus.NOT_FOUND
    }
}
