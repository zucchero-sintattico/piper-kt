package piperkt.services.servers.domain

import piperkt.services.commons.domain.id.ChannelId

enum class ChannelType {
    TEXT,
    MULTIMEDIA
}

open class Channel(
    val channelId: ChannelId,
    val name: String,
    val type: ChannelType,
    val description: String,
    val messages: MutableList<Message> = mutableListOf()
)
