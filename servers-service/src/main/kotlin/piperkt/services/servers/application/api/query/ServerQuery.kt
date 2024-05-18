package piperkt.services.servers.application.api.query

import piperkt.services.servers.application.api.ServiceRequest
import piperkt.services.servers.domain.Server
import piperkt.services.servers.domain.ServerId

sealed interface ServerQuery {
    sealed interface GetServerUsers : ServerQuery {
        data class Request(val serverId: ServerId, override val requestFrom: String) :
            GetServerUsers, ServiceRequest

        data class Response(val users: List<String>) : GetServerUsers
    }

    sealed interface GetServersFromUser : ServerQuery {
        data class Request(override val requestFrom: String) : GetServersFromUser, ServiceRequest

        data class Response(val servers: List<Server>) : GetServersFromUser
    }
}
