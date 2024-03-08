package piperkt.services.servers.presentation.json.response

import org.bson.json.JsonObject

data class CreateServerResponse(
    val name: String,
    val description: String,
)

data class UpdateServerResponse(
    val serverId: String?,
    val name: String?,
    val description: String?,
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
    val servers: List<JsonObject>,
)
