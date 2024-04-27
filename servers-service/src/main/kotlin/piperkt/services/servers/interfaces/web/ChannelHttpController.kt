package piperkt.services.servers.interfaces.web

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import java.security.Principal
import piperkt.common.id.ChannelId
import piperkt.common.id.ServerId
import piperkt.services.servers.application.ChannelService
import piperkt.services.servers.application.api.command.ChannelCommand
import piperkt.services.servers.application.api.query.ChannelQuery
import piperkt.services.servers.interfaces.web.api.ChannelHttpControllerApi
import piperkt.services.servers.interfaces.web.api.dto.ChannelDTO
import piperkt.services.servers.interfaces.web.api.dto.MessageDTO
import piperkt.services.servers.interfaces.web.api.interactions.ChannelApi

@Controller("/servers")
class ChannelHttpController(private val channelService: ChannelService) : ChannelHttpControllerApi {

    @Post("/{serverId}/channels")
    override fun createChannel(
        serverId: String,
        request: ChannelApi.CreateChannelApi.Request,
        principal: Principal
    ): ChannelApi.CreateChannelApi.Response {
        val response =
            channelService
                .createNewChannelInServer(
                    ChannelCommand.CreateNewChannelInServer.Request(
                        channelName = request.name,
                        channelDescription = request.description,
                        channelType = request.type,
                        requestFrom = principal.name,
                        serverId = ServerId(serverId)
                    )
                )
                .getOrThrow()
        return ChannelApi.CreateChannelApi.Response(channelId = response.channelId.value)
    }

    @Put("/{serverId}/channels/{channelId}")
    override fun updateChannel(
        serverId: String,
        channelId: String,
        request: ChannelApi.UpdateChannelApi.Request,
        principal: Principal
    ): ChannelApi.UpdateChannelApi.Response {
        val response =
            channelService
                .updateChannelInServer(
                    ChannelCommand.UpdateChannelInServer.Request(
                        channelId = ChannelId(channelId),
                        channelName = request.name,
                        channelDescription = request.description,
                        requestFrom = principal.name,
                        serverId = ServerId(serverId)
                    )
                )
                .getOrThrow()
        return ChannelApi.UpdateChannelApi.Response(
            name = response.channelName,
            description = response.channelDescription
        )
    }

    @Delete("/{serverId}/channels/{channelId}")
    override fun deleteChannel(
        serverId: String,
        channelId: String,
        principal: Principal
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

    @Get("/{serverId}/channels")
    override fun getChannelsFromServer(
        serverId: String,
        principal: Principal
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

    @Get("/{serverId}/channels/{channelId}/messages")
    override fun getChannelMessages(
        serverId: String,
        channelId: String,
        from: Int,
        to: Int,
        principal: Principal
    ): ChannelApi.GetChannelMessagesApi.Response {
        val response =
            channelService
                .getMessagesFromChannelId(
                    ChannelQuery.GetMessagesFromChannelId.Request(
                        requestFrom = principal.name,
                        channelId = ChannelId(channelId),
                        serverId = ServerId(serverId),
                        from = from,
                        to = to
                    )
                )
                .getOrThrow()
        return ChannelApi.GetChannelMessagesApi.Response(
            messages = response.messages.map { MessageDTO.fromDomain(it) }
        )
    }

    @Post("/{serverId}/channels/{channelId}/messages")
    override fun sendMessageToChannel(
        serverId: String,
        channelId: String,
        request: ChannelApi.SendMessageToChannelApi.Request,
        principal: Principal
    ): ChannelApi.SendMessageToChannelApi.Response {
        val response =
            channelService
                .addMessageInChannel(
                    ChannelCommand.AddMessageInChannel.Request(
                        channelId = ChannelId(channelId),
                        content = request.message,
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
                    content = request.message,
                    sender = principal.name,
                )
        )
    }
}
