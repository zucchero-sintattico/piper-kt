package piperkt.services.servers.domain

import piperkt.common.ddd.Repository

interface ServerRepository : Repository<ServerId, Server> {
    fun findAll(): List<Server>

    override fun findById(id: ServerId): Server?

    fun findByMember(username: String): List<Server>

    fun isUserInServer(serverId: ServerId, username: String): Boolean

    override fun save(entity: Server)

    override fun deleteById(id: ServerId): Server?
}
