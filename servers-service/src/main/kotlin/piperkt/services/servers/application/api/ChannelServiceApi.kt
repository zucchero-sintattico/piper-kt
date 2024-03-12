package piperkt.services.servers.application.api

import piperkt.services.servers.application.api.command.AddMessageInChannelRequest
import piperkt.services.servers.application.api.command.CommandResponse
import piperkt.services.servers.application.api.command.CreateNewChannelInServerRequest
import piperkt.services.servers.application.api.command.DeleteChannelInServerRequest
import piperkt.services.servers.application.api.command.UpdateChannelInServerRequest
import piperkt.services.servers.application.api.query.channels.GetChannelByServerIdRequest
import piperkt.services.servers.application.api.query.channels.GetChannelByServerIdResponse
import piperkt.services.servers.application.api.query.channels.GetMessagesFromChannelIdRequest
import piperkt.services.servers.application.api.query.channels.GetMessagesFromChannelIdResponse

interface ChannelServiceApi {

    fun getChannelsByServerId(request: GetChannelByServerIdRequest): GetChannelByServerIdResponse

    fun createNewChannelInServer(request: CreateNewChannelInServerRequest): CommandResponse

    fun updateChannelInServer(request: UpdateChannelInServerRequest): CommandResponse

    fun deleteChannelInServer(request: DeleteChannelInServerRequest): CommandResponse

    fun getMessagesFromChannelId(
        request: GetMessagesFromChannelIdRequest
    ): GetMessagesFromChannelIdResponse

    fun addMessageInChannel(request: AddMessageInChannelRequest): CommandResponse
}
