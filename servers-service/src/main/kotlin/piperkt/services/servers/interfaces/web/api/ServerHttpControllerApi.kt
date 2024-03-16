package piperkt.services.servers.interfaces.web.api

import piperkt.services.servers.interfaces.web.api.responses.GetServerUsersHttpResponse

interface ServerHttpControllerApi {

    fun getServerUsers(serverId: String): GetServerUsersHttpResponse
}
