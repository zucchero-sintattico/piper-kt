package piperkt.services.servers.domain.factory

import piperkt.common.Factory
import piperkt.common.id.ChannelId
import piperkt.services.servers.domain.Channel
import piperkt.services.servers.domain.ChannelType
import piperkt.services.servers.domain.Message

object ChannelFactory : Factory<Channel> {

    private fun createMessageChannel(
        name: String,
        description: String,
        id: String = ChannelId().value,
        messages: MutableList<Message> = mutableListOf()
    ): Channel {
        return Channel(
            name = name,
            type = ChannelType.TEXT,
            description = description,
            id = ChannelId(id),
            messages = messages
        )
    }

    private fun createMultimediaChannel(
        name: String,
        description: String,
        id: String = ChannelId().value
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
        messages: MutableList<Message> = mutableListOf()
    ): Channel {
        if (ChannelType.valueOf(type) == ChannelType.TEXT) {
            return createMessageChannel(name, description, id, messages)
        }
        return createMultimediaChannel(name, description, id)
    }
}
