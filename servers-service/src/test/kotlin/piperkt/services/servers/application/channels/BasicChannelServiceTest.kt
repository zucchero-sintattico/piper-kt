package piperkt.services.servers.application.channels

import base.UnitTest
import org.mockito.kotlin.mock
import piperkt.events.ChannelEventPublisher
import piperkt.services.servers.application.ChannelService
import piperkt.services.servers.application.ServerRepository
import piperkt.services.servers.domain.Channel
import piperkt.services.servers.domain.ChannelId
import piperkt.services.servers.domain.ChannelMessage
import piperkt.services.servers.domain.ChannelMessageId
import piperkt.services.servers.domain.Server
import piperkt.services.servers.domain.ServerId
import piperkt.services.servers.domain.factory.ChannelFactory
import piperkt.services.servers.domain.factory.MessageFactory
import piperkt.services.servers.domain.factory.ServerFactory

open class BasicChannelServiceTest : UnitTest() {

    protected val serverRepository = mock<ServerRepository>()
    protected val eventPublisher = mock<ChannelEventPublisher>()
    protected val channelService = ChannelService(serverRepository, eventPublisher)

    protected lateinit var simpleServerId: ServerId
    protected lateinit var simpleChannelId: ChannelId
    private lateinit var simpleChannelMessageId: ChannelMessageId
    protected lateinit var simpleServer: Server
    protected lateinit var simpleChannelMessage: ChannelMessage
    private lateinit var simpleChannel: Channel
    protected lateinit var simpleServerWithChannel: Server

    @BeforeEach
    fun setup() {
        simpleServerId = ServerId("0")
        simpleChannelId = ChannelId("0")
        simpleChannelMessageId = ChannelMessageId("0")
        simpleServer =
            ServerFactory.createServer(
                name = "serverName",
                description = "serverDescription",
                owner = "owner",
                id = simpleServerId.value
            )
        simpleChannelMessage =
            MessageFactory.createMessage(
                content = "content",
                sender = "sender",
                id = simpleChannelMessageId.value
            )
        simpleChannel =
            ChannelFactory.createFromType(
                name = "channelName",
                description = "channelDescription",
                type = "TEXT",
                id = simpleChannelId.value,
                channelMessages = mutableListOf(simpleChannelMessage)
            )
        simpleServerWithChannel =
            ServerFactory.createServer(
                name = "serverName",
                description = "serverDescription",
                owner = "owner",
                id = simpleServerId.value,
                channels = listOf(simpleChannel)
            )
    }
}
