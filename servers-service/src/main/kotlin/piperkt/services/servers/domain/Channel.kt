package piperkt.services.servers.domain

import piperkt.common.ddd.Entity

enum class ChannelType {
    TEXT,
    MULTIMEDIA
}

class Channel(
    override val id: ChannelId = ChannelId(),
    var name: String,
    val type: ChannelType,
    var description: String,
    val channelMessages: MutableList<ChannelMessage> = mutableListOf(),
) : Entity<ChannelId>(id) {

    fun addMessage(channelMessage: ChannelMessage) {
        channelMessages.add(channelMessage)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Channel) return false
        if (!super.equals(other)) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + id.hashCode()
        return result
    }
}
