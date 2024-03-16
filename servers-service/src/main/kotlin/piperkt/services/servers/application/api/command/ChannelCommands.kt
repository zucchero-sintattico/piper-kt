package piperkt.services.servers.application.api.command

import piperkt.services.commons.domain.id.ChannelId
import piperkt.services.commons.domain.id.ServerId
import piperkt.services.servers.application.api.ServiceRequest

data class CreateNewChannelInServerRequest(
    val serverId: ServerId,
    val channelName: String,
    val channelDescription: String,
    val channelType: String,
    override val requestFrom: String
) : ServiceRequest

data class UpdateChannelInServerRequest(
    val serverId: ServerId,
    val channelId: ChannelId,
    val channelName: String?,
    val channelDescription: String?,
    val channelType: String,
    override val requestFrom: String
) : ServiceRequest

data class DeleteChannelInServerRequest(
    val serverId: ServerId,
    val channelId: ChannelId,
    override val requestFrom: String
) : ServiceRequest

data class AddMessageInChannelRequest(
    val serverId: ServerId,
    val channelId: ChannelId,
    val content: String,
    val sender: String,
    override val requestFrom: String = sender
) : ServiceRequest
