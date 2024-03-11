package piperkt.services.servers.application.api.query.servers

import piperkt.services.commons.domain.id.ServerId

data class GetServerUsersRequest(val serverId: ServerId)

data class GetServerUsersResponse(
    val users: List<String>,
)
