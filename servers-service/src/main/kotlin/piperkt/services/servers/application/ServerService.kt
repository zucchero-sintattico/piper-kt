package piperkt.services.servers.application

import piperkt.common.events.ServerEvent
import piperkt.common.events.ServerEventPublisher
import piperkt.common.id.ServerId
import piperkt.services.servers.application.api.ServerServiceApi
import piperkt.services.servers.application.api.command.ServerCommand
import piperkt.services.servers.application.api.query.ServerQuery
import piperkt.services.servers.application.exceptions.ServerNotFoundException
import piperkt.services.servers.application.exceptions.UserNotHasPermissionsException
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
            if (isUserAdmin(request.serverId, request.requestFrom)) {
                serverRepository.deleteById(request.serverId)
                eventPublisher.publish(ServerEvent.ServerDeletedEvent(request.serverId))
                Result.success(ServerCommand.DeleteServer.Response)
            } else {
                return Result.failure(UserNotHasPermissionsException())
            }
        } else {
            Result.failure(ServerNotFoundException())
        }
    }

    override fun updateServer(
        request: ServerCommand.UpdateServer.Request
    ): Result<ServerCommand.UpdateServer.Response> {
        val server = serverRepository.findById(request.serverId)
        return if (server != null) {
            if (isUserAdmin(request.serverId, request.requestFrom)) {
                request.name?.let { server.updateName(it) }
                request.description?.let { server.updateDescription(it) }
                serverRepository.update(server)
                eventPublisher.publish(ServerEvent.ServerUpdatedEvent(request.serverId))
                Result.success(ServerCommand.UpdateServer.Response)
            } else {
                return Result.failure(UserNotHasPermissionsException())
            }
        } else {
            Result.failure(ServerNotFoundException())
        }
    }

    override fun addUserToServer(
        request: ServerCommand.AddUserToServer.Request
    ): Result<ServerCommand.AddUserToServer.Response> {
        val server = serverRepository.findById(request.serverId)
        return if (server != null) {
            server.addUser(request.username)
            serverRepository.update(server)
            eventPublisher.publish(ServerEvent.ServerUserAddedEvent(server.id, request.username))
            Result.success(ServerCommand.AddUserToServer.Response)
        } else {
            Result.failure(ServerNotFoundException())
        }
    }

    override fun removeUserFromServer(
        request: ServerCommand.RemoveUserFromServer.Request
    ): Result<ServerCommand.RemoveUserFromServer.Response> {
        val server = serverRepository.findById(request.serverId)
        return if (server != null) {
            server.removeUser(request.username)
            serverRepository.update(server)
            eventPublisher.publish(ServerEvent.ServerUserRemovedEvent(server.id, request.username))
            Result.success(ServerCommand.RemoveUserFromServer.Response)
        } else {
            Result.failure(ServerNotFoundException())
        }
    }

    override fun kickUserFromServer(
        request: ServerCommand.KickUserFromServer.Request
    ): Result<ServerCommand.KickUserFromServer.Response> {
        if (isUserAdmin(request.serverId, request.requestFrom)) {
            val server = serverRepository.findById(request.serverId)
            return if (server != null) {
                server.removeUser(request.username)
                serverRepository.update(server)
                eventPublisher.publish(
                    ServerEvent.ServerUserKickedEvent(server.id, request.username)
                )
                Result.success(ServerCommand.KickUserFromServer.Response)
            } else {
                Result.failure(ServerNotFoundException())
            }
        }
        return Result.failure(UserNotHasPermissionsException())
    }

    override fun getServerUsers(
        request: ServerQuery.GetServerUsers.Request
    ): Result<ServerQuery.GetServerUsers.Response> {
        if (isUserInServer(request.serverId, request.requestFrom)) {
            val server = serverRepository.findById(request.serverId)
            return if (server != null) {
                Result.success(ServerQuery.GetServerUsers.Response(server.users))
            } else {
                Result.failure(ServerNotFoundException())
            }
        }
        return Result.failure(ServerNotFoundException())
    }

    override fun getServersFromUser(
        request: ServerQuery.GetServersFromUser.Request
    ): Result<ServerQuery.GetServersFromUser.Response> {
        if (request.username == request.requestFrom) {
            val servers = serverRepository.findByMember(request.username)
            return Result.success(ServerQuery.GetServersFromUser.Response(servers))
        }
        return Result.failure(UserNotHasPermissionsException())
    }

    private fun isUserAdmin(serverId: ServerId, username: String): Boolean {
        serverRepository.findById(serverId)?.let {
            return it.owner == username
        }
        return false
    }

    private fun isUserInServer(serverId: ServerId, username: String): Boolean {
        return serverRepository.isUserInServer(serverId, username)
    }
}
