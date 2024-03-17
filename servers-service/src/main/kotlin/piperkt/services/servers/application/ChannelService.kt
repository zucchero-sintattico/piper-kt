package piperkt.services.servers.application

import piperkt.services.commons.domain.id.ServerId
import piperkt.services.servers.application.api.ChannelServiceApi
import piperkt.services.servers.application.api.command.ChannelCommand
import piperkt.services.servers.application.api.query.ChannelQuery
import piperkt.services.servers.application.exceptions.ServerOrChannelNotFoundException
import piperkt.services.servers.application.exceptions.UserNotHasPermissionsException
import piperkt.services.servers.application.exceptions.UserNotInServerException

class ChannelService(
    private val channelRepository: ChannelRepository,
    private val serverRepository: ServerRepository
) : ChannelServiceApi {

    override fun createNewChannelInServer(
        request: ChannelCommand.CreateNewChannelInServer.Request
    ): Result<Unit> {
        if (!hasUserPermissions(request.serverId, request.requestFrom)) {
            return Result.failure(UserNotHasPermissionsException())
        }
        val commandResult =
            channelRepository.createChannelInServer(
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

    override fun updateChannelInServer(
        request: ChannelCommand.UpdateChannelInServer.Request
    ): Result<Unit> {
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

    override fun deleteChannelInServer(
        request: ChannelCommand.DeleteChannelInServer.Request
    ): Result<Unit> {
        if (!hasUserPermissions(request.serverId, request.requestFrom)) {
            return Result.failure(UserNotHasPermissionsException())
        }
        val commandSuccess: Boolean =
            channelRepository.deleteChannel(request.serverId, request.channelId)
        return when (commandSuccess) {
            true -> Result.success(Unit)
            false -> Result.failure(ServerOrChannelNotFoundException())
        }
    }

    override fun getChannelsByServerId(
        request: ChannelQuery.GetChannelByServerId.Request
    ): Result<ChannelQuery.GetChannelByServerId.Response> {
        if (serverRepository.findById(request.serverId) == null) {
            return Result.failure(ServerOrChannelNotFoundException())
        }
        val channels = channelRepository.findChannelByServerId(request.serverId)
        return Result.success(ChannelQuery.GetChannelByServerId.Response(channels))
    }

    override fun getMessagesFromChannelId(
        request: ChannelQuery.GetMessagesFromChannelId.Request
    ): Result<ChannelQuery.GetMessagesFromChannelId.Response> {
        if (!serverRepository.isUserInServer(request.serverId, request.requestFrom)) {
            return Result.failure(UserNotInServerException())
        }
        return if (channelRepository.findChannelById(request.channelId) != null) {
            channelRepository
                .getMessagesFromServerIdAndChannelId(request.channelId, request.from, request.to)
                .let { Result.success(ChannelQuery.GetMessagesFromChannelId.Response(it)) }
        } else {
            Result.failure(ServerOrChannelNotFoundException())
        }
    }

    override fun addMessageInChannel(
        request: ChannelCommand.AddMessageInChannel.Request
    ): Result<Unit> {
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
