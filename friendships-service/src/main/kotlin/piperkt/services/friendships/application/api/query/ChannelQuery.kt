package piperkt.services.friendships.application.api.query

import piperkt.common.id.ChannelId
import piperkt.common.id.ServerId
import piperkt.services.friendships.application.api.ServiceRequest
import piperkt.services.friendships.domain.Channel
import piperkt.services.friendships.domain.Message

sealed interface ChannelQuery {
    sealed interface GetMessagesFromChannelId : ChannelQuery {
        data class Request(
            val serverId: ServerId,
            val channelId: ChannelId,
            val from: Int,
            val to: Int,
            override val requestFrom: String
        ) : GetMessagesFromChannelId, ServiceRequest

        data class Response(val messages: List<Message>) : GetMessagesFromChannelId
    }

    sealed interface GetChannelByServerId : ChannelQuery {
        data class Request(val serverId: ServerId, override val requestFrom: String) :
            GetChannelByServerId, ServiceRequest

        data class Response(val channels: List<Channel>) : GetChannelByServerId
    }
}
