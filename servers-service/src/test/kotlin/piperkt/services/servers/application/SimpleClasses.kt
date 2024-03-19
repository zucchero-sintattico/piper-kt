package piperkt.services.servers.application

import piperkt.services.commons.domain.id.ChannelId
import piperkt.services.commons.domain.id.ServerId
import piperkt.services.servers.domain.factory.ChannelFactory
import piperkt.services.servers.domain.factory.MessageFactory
import piperkt.services.servers.domain.factory.ServerFactory

object SimpleClasses {

    fun simpleServerId() = ServerId("000000000000000000000000")

    fun simpleChannelId() = ChannelId("000000000000000000000000")

    fun simpleServer() =
        ServerFactory.createServer(
            simpleServerId().value,
            "serverName",
            "serverDescription",
            "owner"
        )

    fun simpleChannel() =
        ChannelFactory.createFromType(
            simpleChannelId().value,
            "channelName",
            "channelDescription",
            "TEXT"
        )

    fun simpleMessage() =
        MessageFactory.createMessage("000000000000000000000000", "content", "sender")
}
