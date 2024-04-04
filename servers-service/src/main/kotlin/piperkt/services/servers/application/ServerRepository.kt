package piperkt.services.servers.application

import piperkt.common.Repository
import piperkt.common.id.ServerId
import piperkt.services.servers.domain.Server

interface ServerRepository : Repository<ServerId, Server> {
    fun findAll(): List<Server>

    override fun findById(id: ServerId): Server?

    fun findByMember(username: String): List<Server>

    override fun save(entity: Server)

    override fun deleteById(id: ServerId)

    fun update(server: Server): Server

    fun isUserInServer(serverId: ServerId, username: String): Boolean
}
