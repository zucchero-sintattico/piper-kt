package piperkt.services.servers.application.api.command

import piperkt.services.servers.application.api.ServiceRequest
import piperkt.services.servers.domain.ChannelId
import piperkt.services.servers.domain.ChannelMessageId
import piperkt.services.servers.domain.ServerId

sealed interface ChannelCommand {

    sealed interface CreateNewChannelInServer : ChannelCommand {
        data class Request(
            val serverId: ServerId,
            val name: String,
            val description: String,
            val type: String,
            override val requestFrom: String,
        ) : CreateNewChannelInServer, ServiceRequest

        data class Response(val channelId: ChannelId) : CreateNewChannelInServer
    }

    sealed interface UpdateChannelInServer : ChannelCommand {
        data class Request(
            val serverId: ServerId,
            val channelId: ChannelId,
            val newName: String?,
            val newDescription: String?,
            override val requestFrom: String,
        ) : UpdateChannelInServer, ServiceRequest

        data class Response(
            val channelId: ChannelId,
            val newName: String,
            val newDescription: String,
        ) : UpdateChannelInServer
    }

    sealed interface DeleteChannelInServer : ChannelCommand {
        data class Request(
            val serverId: ServerId,
            val channelId: ChannelId,
            override val requestFrom: String,
        ) : DeleteChannelInServer, ServiceRequest

        data class Response(val channelId: ChannelId) : DeleteChannelInServer
    }

    sealed interface AddMessageInChannel : ChannelCommand {
        data class Request(
            val serverId: ServerId,
            val channelId: ChannelId,
            val content: String,
            override val requestFrom: String,
        ) : AddMessageInChannel, ServiceRequest

        data class Response(val channelMessageId: ChannelMessageId) : AddMessageInChannel
    }
}
