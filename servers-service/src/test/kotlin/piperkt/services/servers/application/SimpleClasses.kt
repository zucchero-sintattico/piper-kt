package piperkt.services.servers.application

import piperkt.services.commons.domain.id.ChannelId
import piperkt.services.commons.domain.id.ServerId
import piperkt.services.servers.domain.factory.ChannelFactory
import piperkt.services.servers.domain.factory.MessageFactory
import piperkt.services.servers.domain.factory.ServerFactory

object SimpleClasses {

    fun fakeServerId() = ServerId("000000000000000000000000")

    fun fakeChannelId() = ChannelId("000000000000000000000000")

    fun fakeServer() =
        ServerFactory.createServer(fakeServerId().value, "serverName", "serverDescription", "owner")

    fun fakeChannel() =
        ChannelFactory.createFromType(
            fakeChannelId().value,
            "channelName",
            "channelDescription",
            "TEXT"
        )

    fun fakeMessage() =
        MessageFactory.createMessage("000000000000000000000000", "content", "sender")
}
