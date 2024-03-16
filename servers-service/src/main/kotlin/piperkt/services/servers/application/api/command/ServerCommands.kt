package piperkt.services.servers.application.api.command

import piperkt.services.commons.domain.id.ServerId
import piperkt.services.servers.application.api.ServiceRequest

data class CreateServerRequest(
    val name: String,
    val description: String,
    val owner: String,
    override val requestFrom: String
) : ServiceRequest

data class CreateServerResponse(val serverId: ServerId)

data class UpdateServerRequest(
    val serverId: ServerId,
    val name: String?,
    val description: String?,
    override val requestFrom: String
) : ServiceRequest

data class DeleteServerRequest(val serverId: ServerId, override val requestFrom: String) :
    ServiceRequest

data class AddUserToServerRequest(
    val serverId: ServerId,
    val username: String,
    override val requestFrom: String
) : ServiceRequest

data class RemoveUserFromServerRequest(
    val serverId: ServerId,
    val username: String,
    override val requestFrom: String
) : ServiceRequest

data class KickUserFromServerRequest(
    val serverId: ServerId,
    val username: String,
    override val requestFrom: String
) : ServiceRequest
