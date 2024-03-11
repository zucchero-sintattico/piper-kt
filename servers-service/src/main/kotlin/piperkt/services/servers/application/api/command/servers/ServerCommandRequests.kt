package piperkt.services.servers.application.api.command.servers

import piperkt.services.commons.domain.id.ServerId

data class CreateServerRequest(val name: String, val description: String, val owner: String)

data class UpdateServerRequest(val serverId: ServerId, val name: String?, val description: String?)

data class DeleteServerRequest(val serverId: ServerId)

data class AddUserToServerRequest(val serverId: ServerId, val username: String)

data class RemoveUserFromServerRequest(val serverId: ServerId, val username: String)

data class KickUserFromServerRequest(val serverId: ServerId, val username: String)
