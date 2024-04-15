package piperkt.services.servers.application.api

import piperkt.services.servers.application.api.command.ChannelCommand
import piperkt.services.servers.application.api.query.ChannelQuery

interface ChannelServiceApi {

    fun getChannelsByServerId(
        request: ChannelQuery.GetChannelByServerId.Request
    ): Result<ChannelQuery.GetChannelByServerId.Response>

    fun createNewChannelInServer(
        request: ChannelCommand.CreateNewChannelInServer.Request
    ): Result<ChannelCommand.CreateNewChannelInServer.Response>

    fun updateChannelInServer(
        request: ChannelCommand.UpdateChannelInServer.Request
    ): Result<ChannelCommand.UpdateChannelInServer.Response>

    fun deleteChannelInServer(
        request: ChannelCommand.DeleteChannelInServer.Request
    ): Result<ChannelCommand.DeleteChannelInServer.Response>

    fun getMessagesFromChannelId(
        request: ChannelQuery.GetMessagesFromChannelId.Request
    ): Result<ChannelQuery.GetMessagesFromChannelId.Response>

    fun addMessageInChannel(
        request: ChannelCommand.AddMessageInChannel.Request
    ): Result<ChannelCommand.AddMessageInChannel.Response>
}
