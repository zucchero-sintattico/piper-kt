package piperkt.services.multimedia.domain.servers

interface ServerRepository {
    fun save(server: Server)

    fun findById(id: ServerId): Server?

    fun findAll(): List<Server>

    fun delete(server: Server)

    fun deleteById(id: ServerId)

    fun existsById(id: ServerId): Boolean

    fun count(): Long
}
