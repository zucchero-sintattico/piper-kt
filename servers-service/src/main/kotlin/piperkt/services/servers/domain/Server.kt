package piperkt.services.servers.domain

import piperkt.services.commons.domain.id.ServerId

class Server(
    val id: ServerId,
    var name: String,
    var description: String = "",
    val owner: String,
    var users: List<String> = listOf(owner),
    var channels: List<Channel> = emptyList(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Server

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    fun addUser(username: String) {
        this.users = users.plus(username)
    }

    fun removeUser(username: String) {
        this.users = users.minus(username)
    }

    fun addChannel(channel: Channel) {
        if (!channels.contains(channel)) {
            channels.plus(channel)
        }
    }

    fun removeChannel(channel: Channel) {
        channels.minus(channel)
    }

    fun updateName(name: String) {
        this.name = name
    }

    fun updateDescription(description: String) {
        this.description = description
    }
}
