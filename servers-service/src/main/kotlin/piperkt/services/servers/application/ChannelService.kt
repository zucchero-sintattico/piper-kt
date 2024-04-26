package piperkt.services.servers.application

import piperkt.common.events.ChannelEvent
import piperkt.common.events.ChannelEventPublisher
import piperkt.common.id.ServerId
import piperkt.services.servers.application.api.ChannelServiceApi
import piperkt.services.servers.application.api.command.ChannelCommand
import piperkt.services.servers.application.api.query.ChannelQuery
import piperkt.services.servers.application.exceptions.ServerService
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
                ?: return Result.failure(ServerService.ServerNotFoundException())
        return if (isUserAdmin(request.serverId, request.requestFrom)) {
            val channel =
                ChannelFactory.createFromType(
                    name = request.channelName,
                    description = request.channelDescription,
                    type = request.channelType
                )
                    .also {
                        server.addChannel(it)
                        serverRepository.update(server)
                    }
            eventPublisher.publish(ChannelEvent.ChannelCreatedEvent(channel.id))
            Result.success(ChannelCommand.CreateNewChannelInServer.Response(channel.id))
        } else {
            Result.failure(ServerService.UserNotHasPermissionsException())
        }
    }

    override fun updateChannelInServer(
        request: ChannelCommand.UpdateChannelInServer.Request
    ): Result<ChannelCommand.UpdateChannelInServer.Response> {
        val server =
            serverRepository.findById(request.serverId)
                ?: return Result.failure(ServerService.ServerNotFoundException())
        return if (isUserAdmin(request.serverId, request.requestFrom)) {
            val channel =
                server.channels
                    .find { it.id == request.channelId }
                    ?.also {
                        it.name = request.channelName ?: it.name
                        it.description = request.channelDescription ?: it.description
                        serverRepository.update(server)
                    }
            if (channel != null) {
                eventPublisher.publish(ChannelEvent.ChannelUpdatedEvent(request.channelId))
                Result.success(
                    ChannelCommand.UpdateChannelInServer.Response(
                        channelId = channel.id,
                        channelName = channel.name,
                        channelDescription = channel.description
                    )
                )
            } else {
                Result.failure(ServerService.ChannelNotFoundException())
            }
        } else {
            Result.failure(ServerService.UserNotHasPermissionsException())
        }
    }

    override fun deleteChannelInServer(
        request: ChannelCommand.DeleteChannelInServer.Request
    ): Result<ChannelCommand.DeleteChannelInServer.Response> {
        val server =
            serverRepository.findById(request.serverId)
                ?: return Result.failure(ServerService.ServerNotFoundException())
        return if (isUserAdmin(request.serverId, request.requestFrom)) {
            server.channels
                .find { it.id == request.channelId }
                .let {
                    if (it == null) {
                        Result.failure(ServerService.ChannelNotFoundException())
                    } else {
                        server.removeChannel(it)
                        serverRepository.update(server)
                        eventPublisher.publish(ChannelEvent.ChannelDeletedEvent(request.channelId))
                        Result.success(
                            ChannelCommand.DeleteChannelInServer.Response(
                                channelId = request.channelId
                            )
                        )
                    }
                }
        } else {
            Result.failure(ServerService.UserNotHasPermissionsException())
        }
    }

    override fun getChannelsByServerId(
        request: ChannelQuery.GetChannelByServerId.Request
    ): Result<ChannelQuery.GetChannelByServerId.Response> {
        val server = serverRepository.findById(request.serverId)
        return if (server == null) {
            Result.failure(ServerService.ServerNotFoundException())
        } else {
            if (!server.users.contains(request.requestFrom)) {
                Result.failure(ServerService.UserNotInServerException())
            } else {
                Result.success(ChannelQuery.GetChannelByServerId.Response(server.channels))
            }
        }
    }

    override fun getMessagesFromChannelId(
        request: ChannelQuery.GetMessagesFromChannelId.Request
    ): Result<ChannelQuery.GetMessagesFromChannelId.Response> {
        if (!serverRepository.isUserInServer(request.serverId, request.requestFrom)) {
            return Result.failure(ServerService.UserNotInServerException())
        }
        val server =
            serverRepository.findById(request.serverId)
                ?: return Result.failure(ServerService.ServerNotFoundException())
        val channel = server.channels.find { it.id == request.channelId }
        if (channel == null) {
            return Result.failure(ServerService.ChannelNotFoundException())
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

    override fun addMessageInChannel(
        request: ChannelCommand.AddMessageInChannel.Request
    ): Result<ChannelCommand.AddMessageInChannel.Response> {
        if (!serverRepository.isUserInServer(request.serverId, request.sender)) {
            return Result.failure(ServerService.UserNotInServerException())
        }
        val server =
            serverRepository.findById(request.serverId)
                ?: return Result.failure(ServerService.ServerNotFoundException())
        val channel = server.channels.find { it.id == request.channelId }
        if (channel == null) {
            return Result.failure(ServerService.ChannelNotFoundException())
        }
        val message =
            MessageFactory.createMessage(content = request.content, sender = request.sender)
        channel.addMessage(message)
        serverRepository.update(server)
        eventPublisher.publish(ChannelEvent.MessageInChannelEvent(request.channelId, message.id))
        return Result.success(ChannelCommand.AddMessageInChannel.Response(message.id))
    }

    private fun isUserAdmin(serverId: ServerId, username: String): Boolean {
        serverRepository.findById(serverId)?.let {
            return it.owner == username
        }
        return false
    }
}
