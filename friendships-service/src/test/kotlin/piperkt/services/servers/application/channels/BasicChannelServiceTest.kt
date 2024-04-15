package piperkt.services.servers.application.channels

import io.kotest.core.spec.style.AnnotationSpec
import org.mockito.kotlin.mock
import piperkt.common.events.ChannelEventPublisher
import piperkt.common.id.ChannelId
import piperkt.common.id.MessageId
import piperkt.common.id.ServerId
import piperkt.services.friendships.application.ChannelService
import piperkt.services.friendships.application.ServerRepository
import piperkt.services.friendships.domain.Channel
import piperkt.services.friendships.domain.Message
import piperkt.services.friendships.domain.Server
import piperkt.services.friendships.domain.factory.ChannelFactory
import piperkt.services.friendships.domain.factory.MessageFactory
import piperkt.services.friendships.domain.factory.ServerFactory

open class BasicChannelServiceTest : AnnotationSpec() {

    protected val serverRepository = mock<ServerRepository>()
    protected val eventPublisher = mock<ChannelEventPublisher>()
    protected val channelService = ChannelService(serverRepository, eventPublisher)

    protected lateinit var simpleServerId: ServerId
    protected lateinit var simpleChannelId: ChannelId
    private lateinit var simpleMessageId: MessageId
    protected lateinit var simpleServer: Server
    protected lateinit var simpleMessage: Message
    private lateinit var simpleChannel: Channel
    protected lateinit var simpleServerWithChannel: Server

    @BeforeEach
    fun setup() {
        simpleServerId = ServerId("0")
        simpleChannelId = ChannelId("0")
        simpleMessageId = MessageId("0")
        simpleServer =
            ServerFactory.createServer(
                name = "serverName",
                description = "serverDescription",
                owner = "owner",
                id = simpleServerId.value
            )
        simpleMessage =
            MessageFactory.createMessage(
                content = "content",
                sender = "sender",
                id = simpleMessageId.value
            )
        simpleChannel =
            ChannelFactory.createFromType(
                name = "channelName",
                description = "channelDescription",
                type = "TEXT",
                id = simpleChannelId.value,
                messages = mutableListOf(simpleMessage)
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
