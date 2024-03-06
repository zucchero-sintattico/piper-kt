package piperkt.services.servers.domain

import piperkt.services.servers.commons.id.ChannelId

enum class ChannelType {
    TEXT,
    MULTIMEDIA
}

class Channel(
    val channelId: ChannelId,
    val name: String,
    val type: ChannelType,
    val description: String
)
