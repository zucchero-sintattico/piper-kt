package piperkt.services.servers.application

import piperkt.common.events.ChannelEvent
import piperkt.common.events.ChannelEventPublisher
import piperkt.common.id.ServerId
import piperkt.services.servers.application.api.ChannelServiceApi
import piperkt.services.servers.application.api.command.ChannelCommand
import piperkt.services.servers.application.api.query.ChannelQuery
import piperkt.services.servers.application.exceptions.ServerOrChannelNotFoundException
import piperkt.services.servers.application.exceptions.UserNotHasPermissionsException
import piperkt.services.servers.application.exceptions.UserNotInServerException
import piperkt.services.servers.domain.factory.ChannelFactory
import piperkt.services.servers.domain.factory.MessageFactory

open class ChannelService(
    private val serverRepository: ServerRepository,
    private val eventPublisher: ChannelEventPublisher
) : ChannelServiceApi {

    override fun createNewChannelInServer(
        request: ChannelCommand.CreateNewChannelInServer.Request
    ): Result<ChannelCommand.CreateNewChannelInServer.Response> {
        val server =
            serverRepository.findById(request.serverId)
                ?: return Result.failure(ServerOrChannelNotFoundException())
        return if (isUserAdmin(request.serverId, request.requestFrom)) {
            val channel =
                ChannelFactory.createFromType(
                        channelId = server.channels.size.toString(),
                        name = request.channelName,
                        description = request.channelDescription,
                        type = request.channelType
                    )
                    .also {
                        server.addChannel(it)
                        serverRepository.update(server)
                    }
            eventPublisher.publish(ChannelEvent.ChannelCreatedEvent(channel.channelId))
            Result.success(ChannelCommand.CreateNewChannelInServer.Response(channel.channelId))
        } else {
            Result.failure(UserNotHasPermissionsException())
        }
    }

    override fun updateChannelInServer(
        request: ChannelCommand.UpdateChannelInServer.Request
    ): Result<ChannelCommand.UpdateChannelInServer.Response> {
        val server =
            serverRepository.findById(request.serverId)
                ?: return Result.failure(ServerOrChannelNotFoundException())
        return if (isUserAdmin(request.serverId, request.requestFrom)) {
            val channel =
                server.channels
                    .find { it.channelId == request.channelId }
                    ?.also {
                        it.name = request.channelName ?: it.name
                        it.description = request.channelDescription ?: it.description
                        serverRepository.update(server)
                    }
            if (channel != null) {
                eventPublisher.publish(ChannelEvent.ChannelUpdatedEvent(request.channelId))
                Result.success(ChannelCommand.UpdateChannelInServer.Response)
            } else {
                Result.failure(ServerOrChannelNotFoundException())
            }
        } else {
            Result.failure(UserNotHasPermissionsException())
        }
    }

    override fun deleteChannelInServer(
        request: ChannelCommand.DeleteChannelInServer.Request
    ): Result<ChannelCommand.DeleteChannelInServer.Response> {
        val server =
            serverRepository.findById(request.serverId)
                ?: return Result.failure(ServerOrChannelNotFoundException())
        return if (isUserAdmin(request.serverId, request.requestFrom)) {
            server.channels
                .find { it.channelId == request.channelId }
                .let {
                    if (it == null) {
                        Result.failure(ServerOrChannelNotFoundException())
                    } else {
                        server.removeChannel(it)
                        serverRepository.update(server)
                        eventPublisher.publish(ChannelEvent.ChannelDeletedEvent(request.channelId))
                        Result.success(ChannelCommand.DeleteChannelInServer.Response)
                    }
                }
        } else {
            Result.failure(UserNotHasPermissionsException())
        }
    }

    override fun getChannelsByServerId(
        request: ChannelQuery.GetChannelByServerId.Request
    ): Result<ChannelQuery.GetChannelByServerId.Response> {
        val server = serverRepository.findById(request.serverId)
        return if (server == null) {
            Result.failure(ServerOrChannelNotFoundException())
        } else {
            if (!server.users.contains(request.requestFrom)) {
                Result.failure(UserNotInServerException())
            } else {
                Result.success(ChannelQuery.GetChannelByServerId.Response(server.channels))
            }
        }
    }

    @Suppress("ReturnCount")
    override fun getMessagesFromChannelId(
        request: ChannelQuery.GetMessagesFromChannelId.Request
    ): Result<ChannelQuery.GetMessagesFromChannelId.Response> {
        if (!serverRepository.isUserInServer(request.serverId, request.requestFrom)) {
            return Result.failure(UserNotInServerException())
        }
        val server =
            serverRepository.findById(request.serverId)
                ?: return Result.failure(ServerOrChannelNotFoundException())
        val channel = server.channels.find { it.channelId == request.channelId }
        if (channel == null) {
            return Result.failure(ServerOrChannelNotFoundException())
        }
        return Result.success(
            ChannelQuery.GetMessagesFromChannelId.Response(
                channel.messages.subList(
                    request.from,
                    request.to.coerceAtMost(channel.messages.size)
                )
            )
        )
    }

    @Suppress("ReturnCount")
    override fun addMessageInChannel(
        request: ChannelCommand.AddMessageInChannel.Request
    ): Result<ChannelCommand.AddMessageInChannel.Response> {
        if (!serverRepository.isUserInServer(request.serverId, request.sender)) {
            return Result.failure(UserNotInServerException())
        }
        val server =
            serverRepository.findById(request.serverId)
                ?: return Result.failure(ServerOrChannelNotFoundException())
        val channel = server.channels.find { it.channelId == request.channelId }
        if (channel == null) {
            return Result.failure(ServerOrChannelNotFoundException())
        }
        val message =
            MessageFactory.createMessage(
                messageId = channel.messages.size.toString(),
                content = request.content,
                sender = request.sender
            )
        channel.addMessage(message)
        serverRepository.update(server)
        eventPublisher.publish(
            ChannelEvent.MessageInChannelEvent(request.channelId, message.messageId)
        )
        return Result.success(ChannelCommand.AddMessageInChannel.Response)
    }

    private fun isUserAdmin(serverId: ServerId, username: String): Boolean {
        serverRepository.findById(serverId)?.let {
            return it.owner == username
        }
        return false
    }
}
