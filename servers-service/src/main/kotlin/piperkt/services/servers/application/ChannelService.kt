package piperkt.services.servers.application

import piperkt.services.servers.application.api.ChannelServiceApi
import piperkt.services.servers.application.api.command.CommandResponse
import piperkt.services.servers.application.api.command.CreateNewChannelInServerRequest
import piperkt.services.servers.application.api.command.DeleteChannelInServerRequest
import piperkt.services.servers.application.api.command.UpdateChannelInServerRequest
import piperkt.services.servers.application.api.query.channels.GetChannelByServerIdRequest
import piperkt.services.servers.application.api.query.channels.GetChannelByServerIdResponse

class ChannelService(private val channelRepository: ChannelRepository) : ChannelServiceApi {
    override fun getChannelsByServerId(
        request: GetChannelByServerIdRequest
    ): GetChannelByServerIdResponse {
        val channels = channelRepository.findByServerId(request.serverId)
        return GetChannelByServerIdResponse(channels)
    }

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
        return CommandResponse(true)
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
}
