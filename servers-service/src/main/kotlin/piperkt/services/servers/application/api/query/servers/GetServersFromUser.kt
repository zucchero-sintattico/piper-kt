package piperkt.services.servers.application.api.query.servers

import jakarta.validation.constraints.NotEmpty
import piperkt.services.servers.domain.Server

data class GetServersFromUserRequest(val username: String)

data class GetServersFromUserResponse(
    @NotEmpty val servers: List<Server>,
)
