package piperkt.services.servers.application.api.query.channels

import piperkt.services.commons.domain.id.ServerId
import piperkt.services.servers.application.api.ServiceRequest
import piperkt.services.servers.domain.Channel

data class GetChannelByServerIdRequest(val serverId: ServerId, override val requestFrom: String) :
    ServiceRequest

data class GetChannelByServerIdResponse(
    val channels: List<Channel>,
)
