package piperkt.services.servers.interfaces.web

import io.micronaut.http.annotation.Controller
import java.security.Principal
import piperkt.services.servers.application.ChannelService
import piperkt.services.servers.application.api.command.ChannelCommand
import piperkt.services.servers.application.api.query.ChannelQuery
import piperkt.services.servers.domain.ChannelId
import piperkt.services.servers.domain.ServerId
import piperkt.services.servers.interfaces.web.api.ChannelHttpControllerApi
import piperkt.services.servers.interfaces.web.api.interactions.ChannelApi
import piperkt.services.servers.presentation.ChannelDTO
import piperkt.services.servers.presentation.MessageDTO

@Controller
class ChannelHttpController(private val channelService: ChannelService) : ChannelHttpControllerApi {

    override fun createChannel(
        serverId: String,
        request: ChannelApi.CreateChannelApi.Request,
        principal: Principal,
    ): ChannelApi.CreateChannelApi.Response {
        val response =
            channelService
                .createNewChannelInServer(
                    ChannelCommand.CreateNewChannelInServer.Request(
                        name = request.name,
                        description = request.description,
                        type = request.channelType,
                        requestFrom = principal.name,
                        serverId = ServerId(serverId)
                    )
                )
                .getOrThrow()
        return ChannelApi.CreateChannelApi.Response(channelId = response.channelId.value)
    }

    override fun updateChannel(
        serverId: String,
        channelId: String,
        request: ChannelApi.UpdateChannelApi.Request,
        principal: Principal,
    ): ChannelApi.UpdateChannelApi.Response {
        val response =
            channelService
                .updateChannelInServer(
                    ChannelCommand.UpdateChannelInServer.Request(
                        channelId = ChannelId(channelId),
                        newName = request.name,
                        newDescription = request.description,
                        requestFrom = principal.name,
                        serverId = ServerId(serverId)
                    )
                )
                .getOrThrow()
        return ChannelApi.UpdateChannelApi.Response(
            name = response.newName,
            description = response.newDescription
        )
    }

    override fun deleteChannel(
        serverId: String,
        channelId: String,
        principal: Principal,
    ): ChannelApi.DeleteChannelApi.Response {
        val response =
            channelService
                .deleteChannelInServer(
                    ChannelCommand.DeleteChannelInServer.Request(
                        channelId = ChannelId(channelId),
                        requestFrom = principal.name,
                        serverId = ServerId(serverId)
                    )
                )
                .getOrThrow()
        return ChannelApi.DeleteChannelApi.Response(channelId = response.channelId.value)
    }

    override fun getChannelsFromServer(
        serverId: String,
        principal: Principal,
    ): ChannelApi.GetChannelsFromServerApi.Response {
        val response =
            channelService
                .getChannelsByServerId(
                    ChannelQuery.GetChannelByServerId.Request(
                        requestFrom = principal.name,
                        serverId = ServerId(serverId)
                    )
                )
                .getOrThrow()
        return ChannelApi.GetChannelsFromServerApi.Response(
            channels = response.channels.map { ChannelDTO.fromDomain(it) }
        )
    }

    override fun getChannelMessages(
        serverId: String,
        channelId: String,
        from: Int,
        limit: Int,
        principal: Principal,
    ): ChannelApi.GetChannelMessagesApi.Response {
        val response =
            channelService
                .getMessagesFromChannelId(
                    ChannelQuery.GetMessagesFromChannelId.Request(
                        requestFrom = principal.name,
                        channelId = ChannelId(channelId),
                        serverId = ServerId(serverId),
                        from = from,
                        limit = limit
                    )
                )
                .getOrThrow()
        return ChannelApi.GetChannelMessagesApi.Response(
            messages = response.messages.map { MessageDTO.fromDomain(it) }
        )
    }

    override fun sendMessageToChannel(
        serverId: String,
        channelId: String,
        request: ChannelApi.SendMessageToChannelApi.Request,
        principal: Principal,
    ): ChannelApi.SendMessageToChannelApi.Response {
        val response =
            channelService
                .addMessageInChannel(
                    ChannelCommand.AddMessageInChannel.Request(
                        channelId = ChannelId(channelId),
                        content = request.content,
                        requestFrom = principal.name,
                        serverId = ServerId(serverId)
                    )
                )
                .getOrThrow()
        return ChannelApi.SendMessageToChannelApi.Response(
            channelId = response.messageId.value,
            message =
                MessageDTO(
                    id = response.messageId.value,
                    content = request.content,
                    sender = principal.name,
                )
        )
    }
}
