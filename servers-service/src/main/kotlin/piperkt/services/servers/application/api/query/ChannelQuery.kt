package piperkt.services.servers.application.api.query

import piperkt.services.servers.application.api.ServiceRequest
import piperkt.services.servers.domain.Channel
import piperkt.services.servers.domain.ChannelId
import piperkt.services.servers.domain.Message
import piperkt.services.servers.domain.ServerId

sealed interface ChannelQuery {
    sealed interface GetMessagesFromChannelId : ChannelQuery {
        data class Request(
            val serverId: ServerId,
            val channelId: ChannelId,
            val from: Int,
            val limit: Int,
            override val requestFrom: String,
        ) : GetMessagesFromChannelId, ServiceRequest

        data class Response(val messages: List<Message>) : GetMessagesFromChannelId
    }

    sealed interface GetChannelByServerId : ChannelQuery {
        data class Request(val serverId: ServerId, override val requestFrom: String) :
            GetChannelByServerId, ServiceRequest

        data class Response(val channels: List<Channel>) : GetChannelByServerId
    }
}
