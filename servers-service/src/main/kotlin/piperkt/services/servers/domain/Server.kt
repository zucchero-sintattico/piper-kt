package piperkt.services.servers.domain

import piperkt.common.id.ServerId

class Server(
    val id: ServerId,
    var name: String,
    var description: String = "",
    val owner: String,
    var users: MutableList<String> = mutableListOf(owner),
    var channels: MutableList<Channel> = mutableListOf(),
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
        this.users.add(username)
    }

    fun removeUser(username: String) {
        this.users.remove(username)
    }

    fun addChannel(channel: Channel) {
        this.channels.add(channel)
    }

    fun removeChannel(channel: Channel) {
        this.channels.remove(channel)
    }

    fun updateName(name: String) {
        this.name = name
    }

    fun updateDescription(description: String) {
        this.description = description
    }
}
