package piperkt.services.servers.application

import piperkt.services.servers.application.api.ChannelServiceApi
import piperkt.services.servers.application.api.command.AddMessageInChannelRequest
import piperkt.services.servers.application.api.command.CommandResponse
import piperkt.services.servers.application.api.command.CreateNewChannelInServerRequest
import piperkt.services.servers.application.api.command.DeleteChannelInServerRequest
import piperkt.services.servers.application.api.command.UpdateChannelInServerRequest
import piperkt.services.servers.application.api.query.channels.GetChannelByServerIdRequest
import piperkt.services.servers.application.api.query.channels.GetChannelByServerIdResponse
import piperkt.services.servers.application.api.query.channels.GetMessagesFromChannelIdRequest
import piperkt.services.servers.application.api.query.channels.GetMessagesFromChannelIdResponse

class ChannelService(private val channelRepository: ChannelRepository) : ChannelServiceApi {

    override fun createNewChannelInServer(
        request: CreateNewChannelInServerRequest
    ): CommandResponse {
        val commandResult =
            channelRepository.save(
                request.serverId,
                request.channelName,
                request.channelDescription,
                request.channelType
            )
        return CommandResponse(commandResult != null)
    }

    override fun updateChannelInServer(request: UpdateChannelInServerRequest): CommandResponse {
        val commandResult =
            channelRepository.updateChannel(
                request.serverId,
                request.channelId,
                request.channelName,
                request.channelDescription
            )
        return CommandResponse(commandResult != null)
    }

    override fun deleteChannelInServer(request: DeleteChannelInServerRequest): CommandResponse {
        val commandResult = channelRepository.delete(request.serverId, request.channelId)
        return CommandResponse(commandResult)
    }

    override fun getChannelsByServerId(
        request: GetChannelByServerIdRequest
    ): GetChannelByServerIdResponse {
        val channels = channelRepository.findByServerId(request.serverId)
        return GetChannelByServerIdResponse(channels)
    }

    override fun getMessagesFromChannelId(
        request: GetMessagesFromChannelIdRequest
    ): GetMessagesFromChannelIdResponse {
        val messages =
            channelRepository.getMessagesFromServerIdAndChannelId(
                request.channelId,
                request.from,
                request.to
            )
        return GetMessagesFromChannelIdResponse(messages)
    }

    override fun addMessageInChannel(request: AddMessageInChannelRequest): CommandResponse {
        val commandResult =
            channelRepository.addMessageInChannel(
                request.serverId,
                request.channelId,
                request.content,
                request.sender
            )
        return CommandResponse(commandResult)
    }
}
