package piperkt.services.servers.application.api

import piperkt.services.commons.domain.id.ServerId
import piperkt.services.servers.domain.Server

interface ServerServiceApi {
    fun getServersFromUser(username: String): List<Server>

    fun createServer(
        name: String,
        description: String,
        ownerUsername: String,
    ): Server

    fun deleteServer(
        serverId: ServerId,
    ): Boolean

    fun updateServer(
        serverId: ServerId,
        name: String?,
        description: String?,
    ): Server?

    fun addUserToServer(serverId: ServerId, username: String): Server?

    fun removeUserFromServer(serverId: ServerId, username: String): Server?

    fun kickUserFromServer(serverId: ServerId, username: String): Server?

    fun getServerUsers(
        serverId: ServerId,
    ): List<String>
}
