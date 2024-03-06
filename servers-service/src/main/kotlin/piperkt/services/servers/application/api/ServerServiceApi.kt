package piperkt.services.servers.application.api

import piperkt.services.servers.application.request.CreateServerRequest
import piperkt.services.servers.domain.Server

interface ServerServiceApi {
    fun getServersFromUser(
        userId: String,
    ): List<Server>

    fun createServer(
        request: CreateServerRequest,
    ): Server

    fun deleteServer(
        serverId: String,
    ): Server?

    fun joinServer(
        serverId: String,
        userId: String,
    ): Server?

    fun leaveServer(
        serverId: String,
        userId: String,
    ): Server?

    fun kickUserFromServer(
        serverId: String,
        userId: String,
    ): Server?
}
