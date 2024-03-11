package piperkt.services.servers.application.api.query.channels

import piperkt.services.commons.domain.id.ServerId
import piperkt.services.servers.domain.Channel

data class GetChannelByServerIdRequest(val serverId: ServerId)

data class GetChannelByServerIdResponse(val channels: List<Channel>)
