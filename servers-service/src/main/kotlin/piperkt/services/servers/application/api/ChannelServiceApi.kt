package piperkt.services.servers.application.api

import piperkt.services.servers.application.api.command.ChannelCommand
import piperkt.services.servers.application.api.query.ChannelQuery

interface ChannelServiceApi {

    fun getChannelsByServerId(
        request: ChannelQuery.GetChannelByServerId.Request
    ): Result<ChannelQuery.GetChannelByServerId.Response>

    fun createNewChannelInServer(
        request: ChannelCommand.CreateNewChannelInServer.Request
    ): Result<Unit>

    fun updateChannelInServer(request: ChannelCommand.UpdateChannelInServer.Request): Result<Unit>

    fun deleteChannelInServer(request: ChannelCommand.DeleteChannelInServer.Request): Result<Unit>

    fun getMessagesFromChannelId(
        request: ChannelQuery.GetMessagesFromChannelId.Request
    ): Result<ChannelQuery.GetMessagesFromChannelId.Response>

    fun addMessageInChannel(request: ChannelCommand.AddMessageInChannel.Request): Result<Unit>
}
