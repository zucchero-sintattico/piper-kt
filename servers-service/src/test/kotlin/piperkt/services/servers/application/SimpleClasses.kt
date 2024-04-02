package piperkt.services.servers.application

import piperkt.services.commons.domain.id.ChannelId
import piperkt.services.commons.domain.id.ServerId
import piperkt.services.servers.domain.factory.ChannelFactory
import piperkt.services.servers.domain.factory.MessageFactory
import piperkt.services.servers.domain.factory.ServerFactory

object SimpleClasses {

    fun simpleServerId() = ServerId("000000000000000000000000")

    fun simpleChannelId() = ChannelId("0")

    fun simpleServer() =
        ServerFactory.createServer(
            simpleServerId().value,
            "serverName",
            "serverDescription",
            "owner",
        )

    fun simpleServerWithChannel() =
        ServerFactory.createServerWithChannels(
            simpleServerId().value,
            "serverName",
            "serverDescription",
            "owner",
            listOf(simpleChannel())
        )

    fun simpleServerWithChannelAndMessage() =
        ServerFactory.createServerWithChannels(
            simpleServerId().value,
            "serverName",
            "serverDescription",
            "owner",
            listOf(simpleChannel().apply { addMessage(simpleMessage()) })
        )

    fun simpleChannel() =
        ChannelFactory.createFromType(
            simpleChannelId().value,
            "channelName",
            "channelDescription",
            "TEXT"
        )

    fun simpleMessage() = MessageFactory.createMessage("0", "content", "sender")
}
