package piperkt.services.friendships.domain

import piperkt.common.Entity
import piperkt.common.id.ChannelId

enum class ChannelType {
    TEXT,
    MULTIMEDIA
}

open class Channel(
    var name: String,
    val type: ChannelType,
    var description: String,
    id: ChannelId = ChannelId(),
    val messages: MutableList<Message> = mutableListOf()
) : Entity<ChannelId>(id) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Channel) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    fun addMessage(message: Message) {
        messages.add(message)
    }
}
