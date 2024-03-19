package piperkt.services.servers.infrastructure

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import piperkt.services.commons.domain.id.ChannelId
import piperkt.services.commons.domain.id.ServerId
import piperkt.services.servers.application.ChannelRepository
import piperkt.services.servers.application.ServerRepository
import piperkt.services.servers.domain.ChannelType

@MicronautTest
class ChannelRepositoryImplTest(
    private val channelRepository: ChannelRepository,
    private val serverRepository: ServerRepository
) : AnnotationSpec() {
    private var serverId: ServerId? = null
    private val fakeServerId: ServerId = ServerId("12345678901d345678901234")
    private val fakeChannelId: ChannelId = ChannelId("12345678901d345678901234")

    @BeforeEach
    fun setUp() {
        serverId = serverRepository.save("serverName", "serverDescription", "serverOwner").id
    }

    @Test
    fun `should create a new channel`() {
        val channel =
            channelRepository.createChannelInServer(
                serverId!!,
                "channelName",
                "channelDescription",
                "TEXT"
            )
        channel shouldNotBe null
        channel!!.channelId.value shouldNotBe ""
        println(channel.channelId)
        channel.name shouldBe "channelName"
        channel.description shouldBe "channelDescription"
        channel.type shouldBe ChannelType.TEXT
        channel.messages shouldBe emptyList()
    }

    @Test
    fun `should not create a channel if server doesn't exist`() {
        val channel =
            channelRepository.createChannelInServer(
                fakeServerId,
                "channelName",
                "channelDescription",
                "TEXT"
            )
        channel shouldBe null
    }

    @Test
    fun `should find channels by server id`() {
        val channel =
            channelRepository.createChannelInServer(
                serverId!!,
                "channelName",
                "channelDescription",
                "TEXT"
            )
        val channels = channelRepository.findChannelByServerId(serverId!!)
        channels.size shouldBe 1
        channels[0].channelId shouldBe channel!!.channelId
    }

    @Test
    fun `should not find channels if server doesn't exist`() {
        val channels = channelRepository.findChannelByServerId(fakeServerId)
        channels.size shouldBe 0
    }

    @Test
    fun `should update a channel`() {
        val channel =
            channelRepository.createChannelInServer(
                serverId!!,
                "channelName",
                "channelDescription",
                "TEXT"
            )
        val updatedChannel =
            channelRepository.updateChannel(
                serverId!!,
                channel!!.channelId,
                "newChannelName",
                "newChannelDescription"
            )
        updatedChannel shouldNotBe null
        updatedChannel!!.channelId shouldBe channel.channelId
        updatedChannel.name shouldBe "newChannelName"
        updatedChannel.description shouldBe "newChannelDescription"
        updatedChannel.type shouldBe ChannelType.TEXT
    }

    @Test
    fun `should not update a channel if server doesn't exist`() {
        val channel =
            channelRepository.createChannelInServer(
                serverId!!,
                "channelName",
                "channelDescription",
                "TEXT"
            )
        val updatedChannel =
            channelRepository.updateChannel(
                fakeServerId,
                channel!!.channelId,
                "newChannelName",
                "newChannelDescription"
            )
        updatedChannel shouldBe null
    }

    @Test
    fun `should not update a channel if channel doesn't exist`() {
        val updatedChannel =
            channelRepository.updateChannel(
                serverId!!,
                fakeChannelId,
                "newChannelName",
                "newChannelDescription"
            )
        updatedChannel shouldBe null
    }

    @Test
    fun `should delete a channel`() {
        val channel =
            channelRepository.createChannelInServer(
                serverId!!,
                "channelName",
                "channelDescription",
                "TEXT"
            )
        val deleted = channelRepository.deleteChannel(serverId!!, channel!!.channelId)
        deleted shouldBe true
        channelRepository.findChannelByServerId(serverId!!).size shouldBe 0
    }

    @Test
    fun `should not delete a channel if server doesn't exist`() {
        val channel =
            channelRepository.createChannelInServer(
                serverId!!,
                "channelName",
                "channelDescription",
                "TEXT"
            )
        val deleted = channelRepository.deleteChannel(fakeServerId, channel!!.channelId)
        deleted shouldBe false
    }

    @Test
    fun `should not delete a channel if channel doesn't exist`() {
        val deleted = channelRepository.deleteChannel(serverId!!, fakeChannelId)
        deleted shouldBe false
    }

    @Test
    fun `should get messages from a channel`() {
        val channel =
            channelRepository.createChannelInServer(
                serverId!!,
                "channelName",
                "channelDescription",
                "TEXT"
            )
        val messages =
            channelRepository.getMessagesFromServerIdAndChannelId(channel!!.channelId, 0, 10)
        messages.size shouldBe 0
    }

    @Test
    fun `should add a message in a channel`() {
        val channel =
            channelRepository.createChannelInServer(
                serverId!!,
                "channelName",
                "channelDescription",
                "TEXT"
            )
        val messageSended =
            channelRepository.addMessageInChannel(
                serverId!!,
                channel!!.channelId,
                "message",
                "sender"
            )
        messageSended shouldNotBe null
        val messages =
            channelRepository.getMessagesFromServerIdAndChannelId(channel.channelId, 0, 10)
        messages.size shouldBe 1
        messages[0].content shouldBe "message"
        messages[0].sender shouldBe "sender"
    }

    @Test
    fun `should not add a message in a channel if server or channel don't exist`() {
        val channel =
            channelRepository.createChannelInServer(
                serverId!!,
                "channelName",
                "channelDescription",
                "TEXT"
            )
        val added =
            channelRepository.addMessageInChannel(
                fakeServerId,
                channel!!.channelId,
                "message",
                "sender"
            )
        added shouldBe null
    }

    @Test
    fun `should get messages ordered`() {
        val channel =
            channelRepository.createChannelInServer(
                serverId!!,
                "channelName",
                "channelDescription",
                "TEXT"
            )
        channelRepository.addMessageInChannel(serverId!!, channel!!.channelId, "message1", "sender")
        channelRepository.addMessageInChannel(serverId!!, channel.channelId, "message2", "sender")
        val messages =
            channelRepository.getMessagesFromServerIdAndChannelId(channel.channelId, 0, 10)
        messages.size shouldBe 2
        messages[0].content shouldBe "message1"
        messages[1].content shouldBe "message2"
    }
}
