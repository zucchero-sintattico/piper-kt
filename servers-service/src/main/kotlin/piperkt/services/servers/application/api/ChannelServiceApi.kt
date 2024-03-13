package piperkt.services.servers.application.api

import piperkt.services.servers.application.api.command.AddMessageInChannelRequest
import piperkt.services.servers.application.api.command.CreateNewChannelInServerRequest
import piperkt.services.servers.application.api.command.DeleteChannelInServerRequest
import piperkt.services.servers.application.api.command.UpdateChannelInServerRequest
import piperkt.services.servers.application.api.query.channels.GetChannelByServerIdRequest
import piperkt.services.servers.application.api.query.channels.GetChannelByServerIdResponse
import piperkt.services.servers.application.api.query.channels.GetMessagesFromChannelIdRequest
import piperkt.services.servers.application.api.query.channels.GetMessagesFromChannelIdResponse

interface ChannelServiceApi {

    fun getChannelsByServerId(
        request: GetChannelByServerIdRequest
    ): Result<GetChannelByServerIdResponse>

    fun createNewChannelInServer(request: CreateNewChannelInServerRequest): Result<Unit>

    fun updateChannelInServer(request: UpdateChannelInServerRequest): Result<Unit>

    fun deleteChannelInServer(request: DeleteChannelInServerRequest): Result<Unit>

    fun getMessagesFromChannelId(
        request: GetMessagesFromChannelIdRequest
    ): Result<GetMessagesFromChannelIdResponse>

    fun addMessageInChannel(request: AddMessageInChannelRequest): Result<Unit>
}
