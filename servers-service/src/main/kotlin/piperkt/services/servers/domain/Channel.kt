package piperkt.services.servers.domain

import piperkt.common.ddd.Entity

enum class ChannelType {
    TEXT,
    MULTIMEDIA
}

data class Channel(
    override val id: ChannelId = ChannelId(),
    var name: String,
    val type: ChannelType,
    var description: String,
    val messages: MutableList<Message> = mutableListOf(),
) : Entity<ChannelId>(id) {

    fun addMessage(message: Message) {
        messages.add(message)
    }
}
