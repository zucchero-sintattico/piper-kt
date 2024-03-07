package piperkt.services.servers.application.request

import piperkt.services.commons.domain.id.ServerId
import piperkt.services.commons.domain.id.UserId

data class CreateServerRequest(
    val name: String,
    val description: String,
    val owner: String,
)

data class GetServerFromUserRequest(val userId: UserId)

data class AddMemberToServerRequest(
    val serverId: ServerId,
    val userId: UserId,
)

data class RemoveMemberToServerRequest(
    val serverId: ServerId,
    val userId: UserId,
)

data class KickUserFromServerRequest(
    val serverId: ServerId,
    val userId: UserId,
)

data class DeleteServerRequest(
    val serverId: ServerId,
)

data class GetServerMembersRequest(
    val serverId: ServerId,
)
