package piperkt.services.servers.presentation.json.request

data class CreateServerRequest(val name: String, val description: String, val owner: String)

data class UpdateServerRequest(val serverId: String, val name: String?, val description: String?)

data class DeleteServerRequest(val serverId: String)

data class AddUserToServerRequest(val serverId: String, val username: String)

data class RemoveUserFromServerRequest(val serverId: String, val username: String)

data class KickUserFromServerRequest(val serverId: String, val username: String)

data class GetServerUsersRequest(val serverId: String)

data class GetServersFromUserRequest(val username: String)
