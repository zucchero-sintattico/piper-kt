package piperkt.services.servers.domain.factory

import piperkt.services.commons.domain.id.ChannelId
import piperkt.services.servers.domain.Channel
import piperkt.services.servers.domain.ChannelType

object ChannelFactory {

    fun createMessageChannel(channelId: String, name: String, description: String): Channel {
        return Channel(
            channelId = ChannelId(channelId),
            name = name,
            type = ChannelType.TEXT,
            description = description
        )
    }

    fun createMultimediaChannel(channelId: String, name: String, description: String): Channel {
        return Channel(
            channelId = ChannelId(channelId),
            name = name,
            type = ChannelType.MULTIMEDIA,
            description = description
        )
    }
}
