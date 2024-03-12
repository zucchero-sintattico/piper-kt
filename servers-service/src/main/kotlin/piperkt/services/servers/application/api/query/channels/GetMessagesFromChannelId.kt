package piperkt.services.servers.application.api.query.channels

import piperkt.services.commons.domain.id.ChannelId
import piperkt.services.servers.domain.Message

data class GetMessagesFromChannelIdRequest(val channelId: ChannelId, val from: Int, val to: Int)

data class GetMessagesFromChannelIdResponse(val messages: List<Message>)
