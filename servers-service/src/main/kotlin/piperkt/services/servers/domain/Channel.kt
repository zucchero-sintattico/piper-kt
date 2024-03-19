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
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Channel) return false

        if (channelId != other.channelId) return false

        return true
    }

    override fun hashCode(): Int {
        return channelId.hashCode()
    }
}
