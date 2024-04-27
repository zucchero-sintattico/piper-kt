package piperkt.services.servers.application

import piperkt.common.events.ServerEvent
import piperkt.common.events.ServerEventPublisher
import piperkt.services.servers.application.api.ServerServiceApi
import piperkt.services.servers.application.api.command.ServerCommand
import piperkt.services.servers.application.api.query.ServerQuery
import piperkt.services.servers.application.exceptions.ServerService
import piperkt.services.servers.domain.factory.ServerFactory

open class ServerService(
    private val serverRepository: ServerRepository,
    private val eventPublisher: ServerEventPublisher,
) : ServerServiceApi {
    override fun createServer(
        request: ServerCommand.CreateServer.Request
    ): Result<ServerCommand.CreateServer.Response> {
        val server =
            ServerFactory.createServer(request.name, request.description, request.requestFrom)
        serverRepository.save(server)
        eventPublisher.publish(ServerEvent.ServerCreatedEvent(server.id))
        return Result.success(ServerCommand.CreateServer.Response(server.id))
    }

    override fun deleteServer(
        request: ServerCommand.DeleteServer.Request
    ): Result<ServerCommand.DeleteServer.Response> {
        val server = serverRepository.findById(request.serverId)
        return if (server != null) {
            if (server.isUserAdmin(request.requestFrom)) {
                serverRepository.deleteById(request.serverId)
                eventPublisher.publish(ServerEvent.ServerDeletedEvent(request.serverId))
                Result.success(ServerCommand.DeleteServer.Response(request.serverId))
            } else {
                return Result.failure(ServerService.UserNotHasPermissionsException())
            }
        } else {
            Result.failure(ServerService.ServerNotFoundException())
        }
    }

    override fun updateServer(
        request: ServerCommand.UpdateServer.Request
    ): Result<ServerCommand.UpdateServer.Response> {
        val server = serverRepository.findById(request.serverId)
        return if (server != null) {
            if (server.isUserAdmin(request.requestFrom)) {
                request.name?.let { server.updateName(it) }
                request.description?.let { server.updateDescription(it) }
                serverRepository.update(server)
                eventPublisher.publish(ServerEvent.ServerUpdatedEvent(request.serverId))
                Result.success(
                    ServerCommand.UpdateServer.Response(
                        request.serverId,
                        request.name ?: server.name,
                        request.description ?: server.description
                    )
                )
            } else {
                Result.failure(ServerService.UserNotHasPermissionsException())
            }
        } else {
            Result.failure(ServerService.ServerNotFoundException())
        }
    }

    override fun addUserToServer(
        request: ServerCommand.AddUserToServer.Request
    ): Result<ServerCommand.AddUserToServer.Response> {
        val server = serverRepository.findById(request.serverId)
        return if (server != null) {
            server.addUser(request.requestFrom)
            serverRepository.update(server)
            eventPublisher.publish(ServerEvent.ServerUserAddedEvent(server.id, request.requestFrom))
            Result.success(ServerCommand.AddUserToServer.Response(server.id, request.requestFrom))
        } else {
            Result.failure(ServerService.ServerNotFoundException())
        }
    }

    override fun removeUserFromServer(
        request: ServerCommand.RemoveUserFromServer.Request
    ): Result<ServerCommand.RemoveUserFromServer.Response> {
        val server = serverRepository.findById(request.serverId)
        if (server != null) {
            if (!server.users.contains(request.requestFrom)) {
                return Result.failure(ServerService.UserNotInServerException())
            }
            server.removeUser(request.requestFrom)
            serverRepository.update(server)
            eventPublisher.publish(
                ServerEvent.ServerUserRemovedEvent(server.id, request.requestFrom)
            )
            return Result.success(
                ServerCommand.RemoveUserFromServer.Response(server.id, request.requestFrom)
            )
        } else {
            return Result.failure(ServerService.ServerNotFoundException())
        }
    }

    override fun kickUserFromServer(
        request: ServerCommand.KickUserFromServer.Request
    ): Result<ServerCommand.KickUserFromServer.Response> {
        val server = serverRepository.findById(request.serverId)
        if (server != null) {
            if (!server.isUserAdmin(request.requestFrom)) {
                return Result.failure(ServerService.UserNotHasPermissionsException())
            }
            if (!server.users.contains(request.username)) {
                return Result.failure(ServerService.UserNotInServerException())
            }
            server.removeUser(request.username)
            serverRepository.update(server)
            eventPublisher.publish(ServerEvent.ServerUserKickedEvent(server.id, request.username))
            return Result.success(
                ServerCommand.KickUserFromServer.Response(server.id, request.username)
            )
        } else {
            return Result.failure(ServerService.ServerNotFoundException())
        }
    }

    override fun getServerUsers(
        request: ServerQuery.GetServerUsers.Request
    ): Result<ServerQuery.GetServerUsers.Response> {
        val server = serverRepository.findById(request.serverId)
        return if (server != null) {
            if (server.users.contains(request.requestFrom)) {
                Result.success(ServerQuery.GetServerUsers.Response(server.users))
            } else {
                Result.failure(ServerService.UserNotInServerException())
            }
        } else {
            Result.failure(ServerService.ServerNotFoundException())
        }
    }

    override fun getServersFromUser(
        request: ServerQuery.GetServersFromUser.Request
    ): Result<ServerQuery.GetServersFromUser.Response> {
        val servers = serverRepository.findByMember(request.requestFrom)
        return Result.success(ServerQuery.GetServersFromUser.Response(servers))
    }
}
