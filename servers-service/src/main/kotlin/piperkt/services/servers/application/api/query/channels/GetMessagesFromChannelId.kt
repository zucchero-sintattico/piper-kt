package piperkt.services.servers.application.api.query.channels

import piperkt.services.commons.domain.id.ChannelId
import piperkt.services.commons.domain.id.ServerId
import piperkt.services.servers.application.api.ServiceRequest
import piperkt.services.servers.domain.Message

data class GetMessagesFromChannelIdRequest(
    val serverId: ServerId,
    val channelId: ChannelId,
    val from: Int,
    val to: Int,
    override val requestFrom: String
) : ServiceRequest

data class GetMessagesFromChannelIdResponse(
    val messages: List<Message>,
)
