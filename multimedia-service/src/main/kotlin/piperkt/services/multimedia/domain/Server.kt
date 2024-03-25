package piperkt.services.multimedia.domain

data class ServerId(val value: String)

class Server(val id: ServerId, val users: List<User>, val channels: List<Channel> = emptyList()) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Server

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    companion object {
        fun new(owner: User): Server {
            return Server(ServerId(""), users = listOf(owner))
        }
    }
}

interface ServerRepository {
    fun save(server: Server)

    fun findById(id: ServerId): Server?

    fun findAll(): List<Server>

    fun delete(server: Server)

    fun deleteById(id: ServerId)

    fun existsById(id: ServerId): Boolean

    fun count(): Long

    fun addUser(id: ServerId, user: User)

    fun removeUser(id: ServerId, user: User)
}
