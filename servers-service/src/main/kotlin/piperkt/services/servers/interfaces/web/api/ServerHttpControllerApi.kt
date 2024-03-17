package piperkt.services.servers.interfaces.web.api

import piperkt.services.servers.interfaces.web.api.query.GetServerUsers

interface ServerHttpControllerApi {

    fun getServerUsers(serverId: String): GetServerUsers.HttpResponse
}
