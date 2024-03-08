package piperkt.services.servers.application.api.query

import piperkt.services.commons.domain.id.ServerId

data class GetServerUsersRequest(val serverId: ServerId)

data class GetServersFromUserRequest(val username: String)
