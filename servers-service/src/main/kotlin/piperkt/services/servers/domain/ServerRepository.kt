package piperkt.services.servers.domain

import piperkt.services.commons.domain.id.ServerId

interface ServerRepository {
    fun save(serverName: String, serverDescription: String, owner: String): Server

    fun findByUser(memberUsername: String): List<Server>

    fun deleteServer(serverId: ServerId)

    fun updateServer(serverId: ServerId, serverName: String?, serverDescription: String?): Server?

    fun getServerUsers(serverId: ServerId): List<String>

    fun addUserToServer(serverId: ServerId, username: String): Server?

    fun removeUserFromServer(serverId: ServerId, username: String): Server?
}
