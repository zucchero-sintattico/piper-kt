package piperkt.services.servers.interfaces.web.api

import piperkt.services.servers.interfaces.web.api.servers.CreateServerApi
import piperkt.services.servers.interfaces.web.api.servers.GetServerUsersApi

interface ServerHttpControllerApi {

    fun createServer(request: CreateServerApi.Request): CreateServerApi.Response

    fun getServerUsers(serverId: String): GetServerUsersApi.Response
}
