package piperkt.services.servers.application.api.query.servers

import piperkt.services.servers.application.api.ServiceRequest
import piperkt.services.servers.domain.Server

data class GetServersFromUserRequest(val username: String, override val requestFrom: String) :
    ServiceRequest

data class GetServersFromUserResponse(
    val servers: List<Server>,
)
