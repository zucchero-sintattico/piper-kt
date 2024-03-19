package piperkt.services.servers.domain

import piperkt.services.commons.domain.id.ServerId

class Server(
    val id: ServerId,
    val name: String,
    val description: String = "",
    val owner: String,
    val users: List<String> = listOf(owner),
    val channels: List<Channel> = emptyList(),
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
}
