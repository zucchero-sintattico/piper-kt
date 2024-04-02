package piperkt.services.servers.application

import piperkt.services.commons.domain.id.ServerId
import piperkt.services.servers.domain.Server

interface ServerRepository {
    fun findAll(): List<Server>

    fun findById(serverId: ServerId): Server?

    fun findByMember(username: String): List<Server>

    fun save(serverName: String, serverDescription: String, owner: String): Server

    fun delete(serverId: ServerId)

    fun update(server: Server): Server

    fun isUserInServer(serverId: ServerId, username: String): Boolean
}
