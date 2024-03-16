package piperkt.services.servers.application.api.query.servers

import piperkt.services.commons.domain.id.ServerId
import piperkt.services.servers.application.api.ServiceRequest

data class GetServerUsersQuery(val serverId: ServerId, override val requestFrom: String) :
    ServiceRequest

data class GetServerUsersQueryResponse(
    val users: List<String>,
)
