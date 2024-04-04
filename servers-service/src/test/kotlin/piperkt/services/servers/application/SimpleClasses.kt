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

    fun simpleMessageId() = MessageId("0")

    fun simpleServer() =
        ServerFactory.createServer(
            "serverName",
            "serverDescription",
            "owner",
            id = simpleServerId().value
        )

    fun simpleServerWithChannel() =
        ServerFactory.createServer(
            "serverName",
            "serverDescription",
            "owner",
            listOf(simpleChannel()),
            id = simpleServerId().value
        )

    fun simpleServerWithChannelAndMessage() =
        ServerFactory.createServerWithChannels(
            "serverName",
            "serverDescription",
            "owner",
            listOf(simpleChannel().apply { addMessage(simpleMessage()) })
        )

    fun simpleChannel() =
        ChannelFactory.createFromType(
            "channelName",
            "channelDescription",
            "TEXT",
            id = simpleChannelId().value
        )

    fun simpleMessage() =
        MessageFactory.createMessage("content", "sender", id = simpleMessageId().value)
}
