package piperkt.services.servers.domain.factory

import piperkt.common.id.ChannelId
import piperkt.services.servers.domain.Channel
import piperkt.services.servers.domain.ChannelType

object ChannelFactory {

    private fun createMessageChannel(
        channelId: String,
        name: String,
        description: String
    ): Channel {
        return Channel(
            channelId = ChannelId(channelId),
            name = name,
            type = ChannelType.TEXT,
            description = description
        )
    }

    private fun createMultimediaChannel(
        channelId: String,
        name: String,
        description: String
    ): Channel {
        return Channel(
            channelId = ChannelId(channelId),
            name = name,
            type = ChannelType.MULTIMEDIA,
            description = description
        )
    }

    fun createFromType(
        channelId: String,
        name: String,
        description: String,
        type: String
    ): Channel {
        if (type == "TEXT") {
            return createMessageChannel(channelId, name, description)
        }
        return createMultimediaChannel(channelId, name, description)
    }
}
