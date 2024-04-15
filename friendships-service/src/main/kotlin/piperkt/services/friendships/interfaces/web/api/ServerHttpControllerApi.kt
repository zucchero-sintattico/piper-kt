package piperkt.services.friendships.interfaces.web.api

import piperkt.services.friendships.interfaces.web.api.servers.CreateServerApi
import piperkt.services.friendships.interfaces.web.api.servers.GetServerUsersApi

interface ServerHttpControllerApi {

    fun createServer(request: CreateServerApi.Request): CreateServerApi.Response

    fun getServerUsers(serverId: String): GetServerUsersApi.Response
}
