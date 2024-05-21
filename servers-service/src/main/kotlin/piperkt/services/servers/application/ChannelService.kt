package piperkt.services.servers.application

import piperkt.events.ChannelCreatedEvent
import piperkt.events.ChannelDeletedEvent
import piperkt.events.ChannelEventPublisher
import piperkt.events.ChannelUpdatedEvent
import piperkt.events.MessageInChannelEvent
import piperkt.services.servers.application.api.ChannelServiceApi
import piperkt.services.servers.application.api.command.ChannelCommand
import piperkt.services.servers.application.api.query.ChannelQuery
import piperkt.services.servers.application.exceptions.ServerServiceException
import piperkt.services.servers.domain.factory.ChannelFactory
import piperkt.services.servers.domain.factory.MessageFactory

open class ChannelService(
    private val serverRepository: ServerRepository,
    private val eventPublisher: ChannelEventPublisher,
) : ChannelServiceApi {

    override fun createNewChannelInServer(
        request: ChannelCommand.CreateNewChannelInServer.Request,
    ): Result<ChannelCommand.CreateNewChannelInServer.Response> {
        val server =
            serverRepository.findById(request.serverId)
                ?: return Result.failure(ServerServiceException.ServerNotFoundExceptionException())
        return if (server.isUserAdmin(request.requestFrom)) {
            val channel =
                ChannelFactory.createFromType(
                        name = request.name,
                        description = request.description,
                        type = request.type
                    )
                    .also {
                        server.addChannel(it)
                        serverRepository.update(server)
                    }
            eventPublisher.publish(ChannelCreatedEvent(server.id.value, channel.id.value))
            Result.success(ChannelCommand.CreateNewChannelInServer.Response(channel.id))
        } else {
            Result.failure(ServerServiceException.UserNotHasPermissionsException())
        }
    }

    override fun updateChannelInServer(
        request: ChannelCommand.UpdateChannelInServer.Request,
    ): Result<ChannelCommand.UpdateChannelInServer.Response> {
        val server =
            serverRepository.findById(request.serverId)
                ?: return Result.failure(ServerServiceException.ServerNotFoundExceptionException())
        return if (server.isUserAdmin(request.requestFrom)) {
            val channel =
                server.channels
                    .find { it.id == request.channelId }
                    ?.also {
                        it.name = request.newName ?: it.name
                        it.description = request.newDescription ?: it.description
                        serverRepository.update(server)
                    }
            if (channel != null) {
                eventPublisher.publish(
                    ChannelUpdatedEvent(server.id.value, request.channelId.value)
                )
                Result.success(
                    ChannelCommand.UpdateChannelInServer.Response(
                        channelId = channel.id,
                        newName = channel.name,
                        newDescription = channel.description
                    )
                )
            } else {
                Result.failure(ServerServiceException.ChannelNotFoundException())
            }
        } else {
            Result.failure(ServerServiceException.UserNotHasPermissionsException())
        }
    }

    override fun deleteChannelInServer(
        request: ChannelCommand.DeleteChannelInServer.Request,
    ): Result<ChannelCommand.DeleteChannelInServer.Response> {
        val server =
            serverRepository.findById(request.serverId)
                ?: return Result.failure(ServerServiceException.ServerNotFoundExceptionException())
        return if (server.isUserAdmin(request.requestFrom)) {
            server.channels
                .find { it.id == request.channelId }
                .let {
                    if (it == null) {
                        Result.failure(ServerServiceException.ChannelNotFoundException())
                    } else {
                        server.removeChannel(it)
                        serverRepository.update(server)
                        eventPublisher.publish(
                            ChannelDeletedEvent(server.id.value, request.channelId.value)
                        )
                        Result.success(
                            ChannelCommand.DeleteChannelInServer.Response(
                                channelId = request.channelId
                            )
                        )
                    }
                }
        } else {
            Result.failure(ServerServiceException.UserNotHasPermissionsException())
        }
    }

    override fun getChannelsByServerId(
        request: ChannelQuery.GetChannelByServerId.Request,
    ): Result<ChannelQuery.GetChannelByServerId.Response> {
        val server = serverRepository.findById(request.serverId)
        return if (server == null) {
            Result.failure(ServerServiceException.ServerNotFoundExceptionException())
        } else {
            if (!server.users.contains(request.requestFrom)) {
                Result.failure(ServerServiceException.UserNotHasPermissionsException())
            } else {
                Result.success(ChannelQuery.GetChannelByServerId.Response(server.channels))
            }
        }
    }

    override fun getMessagesFromChannelId(
        request: ChannelQuery.GetMessagesFromChannelId.Request,
    ): Result<ChannelQuery.GetMessagesFromChannelId.Response> {
        if (!serverRepository.isUserInServer(request.serverId, request.requestFrom)) {
            return Result.failure(ServerServiceException.UserNotHasPermissionsException())
        }
        val server =
            serverRepository.findById(request.serverId)
                ?: return Result.failure(ServerServiceException.ServerNotFoundExceptionException())
        val channel = server.channels.find { it.id == request.channelId }
        if (channel == null) {
            return Result.failure(ServerServiceException.ChannelNotFoundException())
        }
        return Result.success(
            ChannelQuery.GetMessagesFromChannelId.Response(
                channel.messages.subList(
                    request.from,
                    request.limit.coerceAtMost(channel.messages.size)
                )
            )
        )
    }

    override fun addMessageInChannel(
        request: ChannelCommand.AddMessageInChannel.Request,
    ): Result<ChannelCommand.AddMessageInChannel.Response> {
        if (!serverRepository.isUserInServer(request.serverId, request.requestFrom)) {
            return Result.failure(ServerServiceException.UserNotHasPermissionsException())
        }
        val server =
            serverRepository.findById(request.serverId)
                ?: return Result.failure(ServerServiceException.ServerNotFoundExceptionException())
        val channel = server.channels.find { it.id == request.channelId }
        if (channel == null) {
            return Result.failure(ServerServiceException.ChannelNotFoundException())
        }
        val message =
            MessageFactory.createMessage(content = request.content, sender = request.requestFrom)
        channel.addMessage(message)
        serverRepository.update(server)
        eventPublisher.publish(
            MessageInChannelEvent(
                serverId = server.id.value,
                channelId = request.channelId.value,
                messageId = message.id.value,
                content = message.content,
                sender = message.sender
            )
        )
        return Result.success(ChannelCommand.AddMessageInChannel.Response(message.id))
    }
}
