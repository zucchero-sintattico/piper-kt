package piperkt.services.servers.application.api.command

import piperkt.services.commons.domain.id.ChannelId
import piperkt.services.commons.domain.id.ServerId
import piperkt.services.servers.application.api.ServiceRequest

sealed interface ChannelCommand {

    sealed interface CreateNewChannelInServer : ChannelCommand {
        data class Request(
            val serverId: ServerId,
            val channelName: String,
            val channelDescription: String,
            val channelType: String,
            override val requestFrom: String
        ) : CreateNewChannelInServer, ServiceRequest

        data class Response(val channelId: ChannelId) : CreateNewChannelInServer
    }

    sealed interface UpdateChannelInServer : ChannelCommand {
        data class Request(
            val serverId: ServerId,
            val channelId: ChannelId,
            val channelName: String?,
            val channelDescription: String?,
            val channelType: String,
            override val requestFrom: String
        ) : UpdateChannelInServer, ServiceRequest

        data object Response : UpdateChannelInServer
    }

    sealed interface DeleteChannelInServer : ChannelCommand {
        data class Request(
            val serverId: ServerId,
            val channelId: ChannelId,
            override val requestFrom: String
        ) : DeleteChannelInServer, ServiceRequest

        data object Response : DeleteChannelInServer
    }

    sealed interface AddMessageInChannel : ChannelCommand {
        data class Request(
            val serverId: ServerId,
            val channelId: ChannelId,
            val content: String,
            val sender: String,
            override val requestFrom: String = sender
        ) : AddMessageInChannel, ServiceRequest

        data object Response : AddMessageInChannel
    }
}
