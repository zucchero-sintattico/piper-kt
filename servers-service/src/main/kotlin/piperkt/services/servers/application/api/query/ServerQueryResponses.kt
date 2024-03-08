package piperkt.services.servers.application.api.query

import piperkt.services.servers.domain.Server

data class GetServerUsersResponse(
    val success: Boolean,
    val users: List<String>,
)

data class GetServersFromUserResponse(
    val servers: List<Server>,
)
