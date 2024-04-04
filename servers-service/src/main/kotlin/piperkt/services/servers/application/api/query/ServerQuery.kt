package piperkt.services.servers.application.api.query

import piperkt.common.id.ServerId
import piperkt.services.servers.application.api.ServiceRequest
import piperkt.services.servers.domain.Server

sealed interface ServerQuery {
    sealed interface GetServerUsers : ServerQuery {
        data class Request(
            val serverId: ServerId,
            val username: String,
            override val requestFrom: String
        ) : GetServerUsers, ServiceRequest

        data class Response(val users: List<String>) : GetServerUsers
    }

    sealed interface GetServersFromUser : ServerQuery {
        data class Request(val username: String, override val requestFrom: String) :
            GetServersFromUser, ServiceRequest

        data class Response(val servers: List<Server>) : GetServersFromUser
    }
}
