package piperkt.services.servers.application

import piperkt.services.commons.domain.id.ServerId
import piperkt.services.servers.domain.Server

interface ServerRepository {
    fun save(serverName: String, serverDescription: String, owner: String): Server

    fun getServersFromUser(memberUsername: String): List<Server>

    fun deleteServer(serverId: ServerId): Boolean

    fun updateServer(serverId: ServerId, serverName: String?, serverDescription: String?): Server?

    fun getServerUsers(serverId: ServerId): List<String>

    fun addUserToServer(serverId: ServerId, username: String): Server?

    fun removeUserFromServer(serverId: ServerId, username: String): Server?

    fun isUserInServer(serverId: ServerId, username: String): Boolean

    fun findById(serverId: ServerId): Server?
}
