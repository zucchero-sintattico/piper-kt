package piperkt.services.servers.application.api.query.servers

import piperkt.services.commons.domain.id.ServerId
import piperkt.services.servers.application.api.ServiceRequest

data class GetServerUsersRequest(val serverId: ServerId, override val requestFrom: String) :
    ServiceRequest

data class GetServerUsersResponse(
    val users: List<String>,
)
