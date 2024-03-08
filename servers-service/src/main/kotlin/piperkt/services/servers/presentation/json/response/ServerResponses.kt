package piperkt.services.servers.presentation.json.response

import piperkt.services.servers.presentation.json.ServerJson

data class CreateServerResponse(
    val success: Boolean,
)

data class UpdateServerResponse(
    val success: Boolean,
)

data class DeleteServerResponse(
    val success: Boolean,
)

data class AddUserToServerResponse(
    val success: Boolean,
)

data class RemoveUserFromServerResponse(
    val success: Boolean,
)

data class KickUserFromServerResponse(
    val success: Boolean,
)

data class GetServerUsersResponse(
    val success: Boolean,
    val users: List<String>,
)

data class GetServersFromUserResponse(
    val servers: List<ServerJson>,
)
