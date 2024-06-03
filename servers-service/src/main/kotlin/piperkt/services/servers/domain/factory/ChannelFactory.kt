package piperkt.services.servers.domain.factory

import piperkt.common.ddd.Factory
import piperkt.services.servers.domain.Channel
import piperkt.services.servers.domain.ChannelId
import piperkt.services.servers.domain.ChannelMessage
import piperkt.services.servers.domain.ChannelType

object ChannelFactory : Factory<Channel> {

    private fun createMessageChannel(
        name: String,
        description: String,
        id: String = ChannelId().value,
        channelMessages: MutableList<ChannelMessage> = mutableListOf(),
    ): Channel {
        return Channel(
            name = name,
            type = ChannelType.TEXT,
            description = description,
            id = ChannelId(id),
            channelMessages = channelMessages
        )
    }

    private fun createMultimediaChannel(
        name: String,
        description: String,
        id: String = ChannelId().value,
    ): Channel {
        return Channel(
            name = name,
            type = ChannelType.MULTIMEDIA,
            description = description,
            id = ChannelId(id)
        )
    }

    fun createFromType(
        name: String,
        description: String,
        type: String,
        id: String = ChannelId().value,
        channelMessages: MutableList<ChannelMessage> = mutableListOf(),
    ): Channel {
        if (ChannelType.valueOf(type) == ChannelType.TEXT) {
            return createMessageChannel(name, description, id, channelMessages)
        }
        return createMultimediaChannel(name, description, id)
    }
}
