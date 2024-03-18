package piperkt.services.servers.application

import piperkt.services.commons.domain.events.DomainEventPublisher
import piperkt.services.commons.domain.events.ServerEvent
import piperkt.services.commons.domain.id.ServerId
import piperkt.services.servers.application.api.ServerServiceApi
import piperkt.services.servers.application.api.command.ServerCommand
import piperkt.services.servers.application.api.query.ServerQuery
import piperkt.services.servers.application.exceptions.ServerNotFoundException
import piperkt.services.servers.application.exceptions.UserNotHasPermissionsException

open class ServerService(
    private val serverRepository: ServerRepository,
    private val eventPublisher: DomainEventPublisher,
) : ServerServiceApi {
    override fun createServer(
        request: ServerCommand.CreateServer.Request
    ): Result<ServerCommand.CreateServer.Response> {
        val server = serverRepository.save(request.name, request.description, request.owner)
        eventPublisher.publish(ServerEvent.ServerCreatedEvent(server.id))
        return Result.success(ServerCommand.CreateServer.Response(server.id))
    }

    override fun deleteServer(
        request: ServerCommand.DeleteServer.Request
    ): Result<ServerCommand.DeleteServer.Response> {
        if (isUserAdmin(request.serverId, request.requestFrom)) {
            val success = serverRepository.deleteServer(request.serverId)
            return if (success) {
                eventPublisher.publish(ServerEvent.ServerDeletedEvent(request.serverId))
                Result.success(ServerCommand.DeleteServer.Response)
            } else {
                Result.failure(ServerNotFoundException())
            }
        } else {
            return Result.failure(UserNotHasPermissionsException())
        }
    }

    override fun updateServer(
        request: ServerCommand.UpdateServer.Request
    ): Result<ServerCommand.UpdateServer.Response> {
        if (isUserAdmin(request.serverId, request.requestFrom)) {
            val server =
                serverRepository.updateServer(request.serverId, request.name, request.description)
            return if (server != null) {
                eventPublisher.publish(ServerEvent.ServerUpdatedEvent(server.id))
                Result.success(ServerCommand.UpdateServer.Response)
            } else {
                Result.failure(ServerNotFoundException())
            }
        } else {
            return Result.failure(UserNotHasPermissionsException())
        }
    }

    override fun addUserToServer(
        request: ServerCommand.AddUserToServer.Request
    ): Result<ServerCommand.AddUserToServer.Response> {
        val server = serverRepository.addUserToServer(request.serverId, request.username)
        return if (server != null) {
            eventPublisher.publish(ServerEvent.ServerUserAddedEvent(server.id, request.username))
            Result.success(ServerCommand.AddUserToServer.Response)
        } else {
            Result.failure(ServerNotFoundException())
        }
    }

    override fun removeUserFromServer(
        request: ServerCommand.RemoveUserFromServer.Request
    ): Result<ServerCommand.RemoveUserFromServer.Response> {
        val server = serverRepository.removeUserFromServer(request.serverId, request.username)
        return if (server != null) {
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
            val server = serverRepository.removeUserFromServer(request.serverId, request.username)
            return if (server != null) {
                eventPublisher.publish(
                    ServerEvent.ServerUserRemovedEvent(server.id, request.username)
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
            val users: List<String> = serverRepository.getServerUsers(request.serverId)
            return if (users.isNotEmpty()) {
                Result.success(ServerQuery.GetServerUsers.Response(users))
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
            val servers = serverRepository.getServersFromUser(request.username)
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
