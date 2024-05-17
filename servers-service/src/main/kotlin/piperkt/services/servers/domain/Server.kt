package piperkt.services.servers.domain

import piperkt.common.ddd.AggregateRoot
import piperkt.common.id.ServerId

class Server(
    override val id: ServerId = ServerId(),
    var name: String,
    var description: String = "",
    val owner: String,
    var users: MutableList<String> = mutableListOf(owner),
    var channels: MutableList<Channel> = mutableListOf(),
) : AggregateRoot<ServerId>(id) {
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

    fun isUserAdmin(username: String): Boolean {
        return this.owner == username
    }
}
