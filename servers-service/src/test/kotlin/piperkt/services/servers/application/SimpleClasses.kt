package piperkt.services.servers.application

import piperkt.common.id.ChannelId
import piperkt.common.id.MessageId
import piperkt.common.id.ServerId
import piperkt.services.servers.domain.factory.ChannelFactory
import piperkt.services.servers.domain.factory.MessageFactory
import piperkt.services.servers.domain.factory.ServerFactory

object SimpleClasses {

    fun simpleServerId() = ServerId("0")

    fun simpleChannelId() = ChannelId("0")

    private fun simpleMessageId() = MessageId("0")

    fun simpleServer() =
        ServerFactory.createServer(
            name = "serverName",
            description = "serverDescription",
            owner = "owner",
            id = simpleServerId().value
        )

    fun simpleServerWithChannel() =
        ServerFactory.createServer(
            name = "serverName",
            description = "serverDescription",
            owner = "owner",
            id = simpleServerId().value,
            channels = listOf(simpleChannel)
        )

    private val simpleChannel =
        ChannelFactory.createFromType(
            name = "channelName",
            description = "channelDescription",
            type = "TEXT",
            id = simpleChannelId().value,
            messages = mutableListOf(simpleMessage())
        )

    fun simpleMessage() =
        MessageFactory.createMessage(
            content = "content",
            sender = "sender",
            id = simpleMessageId().value
        )
}
