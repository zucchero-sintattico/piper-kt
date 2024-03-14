package piperkt.services.servers.application

import piperkt.services.commons.domain.id.ServerId
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
import piperkt.services.servers.application.exceptions.UserNotHasPermissionsException
import piperkt.services.servers.application.exceptions.UserNotInServerException

class ChannelService(
    private val channelRepository: ChannelRepository,
    private val serverRepository: ServerRepository
) : ChannelServiceApi {

    override fun createNewChannelInServer(request: CreateNewChannelInServerRequest): Result<Unit> {
        if (!hasUserPermissions(request.serverId, request.requestFrom)) {
            return Result.failure(UserNotHasPermissionsException())
        }
        val commandResult =
            channelRepository.save(
                request.serverId,
                request.channelName,
                request.channelDescription,
                request.channelType
            )
        return when (commandResult != null) {
            true -> Result.success(Unit)
            false -> Result.failure(ServerOrChannelNotFoundException())
        }
    }

    override fun updateChannelInServer(request: UpdateChannelInServerRequest): Result<Unit> {
        if (!hasUserPermissions(request.serverId, request.requestFrom)) {
            return Result.failure(UserNotHasPermissionsException())
        }
        val commandResult =
            channelRepository.updateChannel(
                request.serverId,
                request.channelId,
                request.channelName,
                request.channelDescription
            )
        return when (commandResult != null) {
            true -> Result.success(Unit)
            false -> Result.failure(ServerOrChannelNotFoundException())
        }
    }

    override fun deleteChannelInServer(request: DeleteChannelInServerRequest): Result<Unit> {
        if (!hasUserPermissions(request.serverId, request.requestFrom)) {
            return Result.failure(UserNotHasPermissionsException())
        }
        val commandSuccess: Boolean = channelRepository.delete(request.serverId, request.channelId)
        return when (commandSuccess) {
            true -> Result.success(Unit)
            false -> Result.failure(ServerOrChannelNotFoundException())
        }
    }

    override fun getChannelsByServerId(
        request: GetChannelByServerIdRequest
    ): Result<GetChannelByServerIdResponse> {
        if (serverRepository.findById(request.serverId) == null) {
            return Result.failure(ServerOrChannelNotFoundException())
        }
        val channels = channelRepository.findByServerId(request.serverId)
        return Result.success(GetChannelByServerIdResponse(channels))
    }

    override fun getMessagesFromChannelId(
        request: GetMessagesFromChannelIdRequest
    ): Result<GetMessagesFromChannelIdResponse> {
        if (!serverRepository.isUserInServer(request.serverId, request.requestFrom)) {
            return Result.failure(UserNotInServerException())
        }
        return if (channelRepository.findByChannelId(request.channelId) != null) {
            channelRepository
                .getMessagesFromServerIdAndChannelId(request.channelId, request.from, request.to)
                .let { Result.success(GetMessagesFromChannelIdResponse(it)) }
        } else {
            Result.failure(ServerOrChannelNotFoundException())
        }
    }

    override fun addMessageInChannel(request: AddMessageInChannelRequest): Result<Unit> {
        if (!serverRepository.isUserInServer(request.serverId, request.sender)) {
            return Result.failure(UserNotInServerException())
        }
        val commandSuccess: Boolean =
            channelRepository.addMessageInChannel(
                request.serverId,
                request.channelId,
                request.content,
                request.sender
            )
        return when (commandSuccess) {
            true -> Result.success(Unit)
            false -> Result.failure(ServerOrChannelNotFoundException())
        }
    }

    private fun hasUserPermissions(serverId: ServerId, username: String): Boolean {
        serverRepository.findById(serverId)?.let {
            return it.owner == username
        }
        return false
    }
}
