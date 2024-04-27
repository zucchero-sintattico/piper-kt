package piperkt.services.servers.interfaces.web.api.interactions

import io.micronaut.serde.annotation.Serdeable
import piperkt.services.servers.interfaces.web.api.dto.ChannelDTO
import piperkt.services.servers.interfaces.web.api.dto.MessageDTO

sealed interface ChannelApi {

    sealed interface CreateChannelApi : ChannelApi {

        @Serdeable
        data class Request(val name: String, val description: String, val type: String) :
            CreateChannelApi

        @Serdeable data class Response(val channelId: String) : CreateChannelApi
    }

    sealed interface UpdateChannelApi : ChannelApi {
        @Serdeable data class Request(val name: String, val description: String) : UpdateChannelApi

        @Serdeable
        data class Response(val name: String, val description: String) : UpdateChannelApi
    }

    sealed interface DeleteChannelApi : ChannelApi {
        @Serdeable data class Response(val channelId: String) : DeleteChannelApi
    }

    sealed interface GetChannelsFromServerApi : ChannelApi {

        // Don't need a body for this request
        @Serdeable data class Response(val channels: List<ChannelDTO>) : GetChannelsFromServerApi
    }

    sealed interface GetChannelMessagesApi : ChannelApi {

        // Don't need a body for this request
        @Serdeable data class Response(val messages: List<MessageDTO>) : GetChannelMessagesApi
    }

    sealed interface SendMessageToChannelApi : ChannelApi {

        @Serdeable data class Request(val message: String) : SendMessageToChannelApi

        @Serdeable
        data class Response(val channelId: String, val message: MessageDTO) :
            SendMessageToChannelApi
    }
}
