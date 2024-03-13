package piperkt.services.servers.application

import piperkt.services.servers.application.api.ChannelServiceApi
import piperkt.services.servers.application.api.command.AddMessageInChannelRequest
import piperkt.services.servers.application.api.command.CreateNewChannelInServerRequest
import piperkt.services.servers.application.api.command.DeleteChannelInServerRequest
import piperkt.services.servers.application.api.command.UpdateChannelInServerRequest
import piperkt.services.servers.application.api.query.channels.GetChannelByServerIdRequest
import piperkt.services.servers.application.api.query.channels.GetChannelByServerIdResponse
import piperkt.services.servers.application.api.query.channels.GetMessagesFromChannelIdRequest
import piperkt.services.servers.application.api.query.channels.GetMessagesFromChannelIdResponse
import piperkt.services.servers.application.exceptions.ServerOrChannelNotFoundException
import piperkt.services.servers.application.exceptions.UserNotInServerException

class ChannelService(
    private val channelRepository: ChannelRepository,
    private val serverRepository: ServerRepository
) : ChannelServiceApi {

    override fun createNewChannelInServer(request: CreateNewChannelInServerRequest): Result<Unit> {
        val commandResult =
            channelRepository.save(
                request.serverId,
                request.channelName,
                request.channelDescription,
                request.channelType
            )
        if (commandResult == null) {
            return Result.failure(ServerOrChannelNotFoundException())
        }
        return Result.success(Unit)
    }

    override fun updateChannelInServer(request: UpdateChannelInServerRequest): Result<Unit> {
        val commandResult =
            channelRepository.updateChannel(
                request.serverId,
                request.channelId,
                request.channelName,
                request.channelDescription
            )
        if (commandResult == null) {
            return Result.failure(ServerOrChannelNotFoundException())
        }
        return Result.success(Unit)
    }

    override fun deleteChannelInServer(request: DeleteChannelInServerRequest): Result<Unit> {
        val commandSuccess: Boolean = channelRepository.delete(request.serverId, request.channelId)
        if (!commandSuccess) {
            return Result.failure(ServerOrChannelNotFoundException())
        }
        return Result.success(Unit)
    }

    override fun getChannelsByServerId(
        request: GetChannelByServerIdRequest
    ): Result<GetChannelByServerIdResponse> {
        val channels = channelRepository.findByServerId(request.serverId)
        return Result.success(GetChannelByServerIdResponse(channels))
    }

    override fun getMessagesFromChannelId(
        request: GetMessagesFromChannelIdRequest
    ): Result<GetMessagesFromChannelIdResponse> {
        if (!serverRepository.isUserInServer(request.serverId, request.requestFrom)) {
            return Result.failure(UserNotInServerException())
        }
        val messages =
            channelRepository.getMessagesFromServerIdAndChannelId(
                request.channelId,
                request.from,
                request.to
            )
        return Result.success(GetMessagesFromChannelIdResponse(messages))
    }

    @Suppress("ReturnCount")
    override fun addMessageInChannel(request: AddMessageInChannelRequest): Result<Unit> {
        val isUserInServer: Boolean =
            serverRepository.isUserInServer(request.serverId, request.sender)
        if (!isUserInServer) {
            return Result.failure(UserNotInServerException())
        }

        val commandSuccess: Boolean =
            channelRepository.addMessageInChannel(
                request.serverId,
                request.channelId,
                request.content,
                request.sender
            )
        if (!commandSuccess) {
            return Result.failure(ServerOrChannelNotFoundException())
        }
        return Result.success(Unit)
    }
}
