package piperkt.services.servers.application.api

import piperkt.services.servers.application.api.command.ChannelCommand
import piperkt.services.servers.application.api.query.channels.GetChannelByServerIdRequest
import piperkt.services.servers.application.api.query.channels.GetChannelByServerIdResponse
import piperkt.services.servers.application.api.query.channels.GetMessagesFromChannelIdRequest
import piperkt.services.servers.application.api.query.channels.GetMessagesFromChannelIdResponse

interface ChannelServiceApi {

    fun getChannelsByServerId(
        request: GetChannelByServerIdRequest
    ): Result<GetChannelByServerIdResponse>

    fun createNewChannelInServer(
        request: ChannelCommand.CreateNewChannelInServer.Request
    ): Result<Unit>

    fun updateChannelInServer(request: ChannelCommand.UpdateChannelInServer.Request): Result<Unit>

    fun deleteChannelInServer(request: ChannelCommand.DeleteChannelInServer.Request): Result<Unit>

    fun getMessagesFromChannelId(
        request: GetMessagesFromChannelIdRequest
    ): Result<GetMessagesFromChannelIdResponse>

    fun addMessageInChannel(request: ChannelCommand.AddMessageInChannel.Request): Result<Unit>
}
