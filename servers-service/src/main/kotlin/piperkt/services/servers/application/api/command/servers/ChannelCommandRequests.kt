package piperkt.services.servers.application.api.command.servers

import piperkt.services.commons.domain.id.ChannelId
import piperkt.services.commons.domain.id.ServerId

data class CreateNewChannelInServerRequest(
    val serverId: ServerId,
    val channelName: String,
    val channelType: String
)

data class UpdateChannelInServerRequest(
    val serverId: ServerId,
    val channelId: ChannelId,
    val channelName: String,
    val channelType: String
)

data class DeleteChannelInServerRequest(val serverId: ServerId, val channelId: ChannelId)
