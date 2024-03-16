package piperkt.services.servers.application

import piperkt.services.commons.domain.id.ServerId
import piperkt.services.servers.application.api.ServerServiceApi
import piperkt.services.servers.application.api.command.AddUserToServerRequest
import piperkt.services.servers.application.api.command.CreateServerRequest
import piperkt.services.servers.application.api.command.DeleteServerRequest
import piperkt.services.servers.application.api.command.KickUserFromServerRequest
import piperkt.services.servers.application.api.command.RemoveUserFromServerRequest
import piperkt.services.servers.application.api.command.UpdateServerRequest
import piperkt.services.servers.application.api.query.servers.GetServerUsersQueryResponse
import piperkt.services.servers.application.api.query.servers.GetServerUsersRequest
import piperkt.services.servers.application.api.query.servers.GetServersFromUserRequest
import piperkt.services.servers.application.api.query.servers.GetServersFromUserResponse
import piperkt.services.servers.application.exceptions.ServerNotFoundException
import piperkt.services.servers.application.exceptions.UserNotHasPermissionsException

open class ServerService(
    private val serverRepository: ServerRepository,
    // private val eventPublisher: EventPublisher,
) : ServerServiceApi {
    override fun createServer(request: CreateServerRequest): Result<Unit> {
        serverRepository.save(request.name, request.description, request.owner)
        return Result.success(Unit)
    }

    override fun deleteServer(request: DeleteServerRequest): Result<Unit> {
        if (isUserAdmin(request.serverId, request.requestFrom)) {
            val success = serverRepository.deleteServer(request.serverId)
            return if (success) {
                Result.success(Unit)
            } else {
                Result.failure(ServerNotFoundException())
            }
        } else {
            return Result.failure(UserNotHasPermissionsException())
        }
    }

    override fun updateServer(request: UpdateServerRequest): Result<Unit> {
        if (isUserAdmin(request.serverId, request.requestFrom)) {
            val server =
                serverRepository.updateServer(request.serverId, request.name, request.description)
            return if (server != null) {
                Result.success(Unit)
            } else {
                Result.failure(ServerNotFoundException())
            }
        } else {
            return Result.failure(UserNotHasPermissionsException())
        }
    }

    override fun addUserToServer(request: AddUserToServerRequest): Result<Unit> {
        val server = serverRepository.addUserToServer(request.serverId, request.username)
        return if (server != null) {
            Result.success(Unit)
        } else {
            Result.failure(ServerNotFoundException())
        }
    }

    override fun removeUserFromServer(request: RemoveUserFromServerRequest): Result<Unit> {
        val server = serverRepository.removeUserFromServer(request.serverId, request.username)
        return if (server != null) {
            Result.success(Unit)
        } else {
            Result.failure(ServerNotFoundException())
        }
    }

    override fun kickUserFromServer(request: KickUserFromServerRequest): Result<Unit> {
        if (isUserAdmin(request.serverId, request.requestFrom)) {
            val server = serverRepository.removeUserFromServer(request.serverId, request.username)
            return if (server != null) {
                Result.success(Unit)
            } else {
                Result.failure(ServerNotFoundException())
            }
        }
        return Result.failure(UserNotHasPermissionsException())
    }

    override fun getServerUsers(
        request: GetServerUsersRequest
    ): Result<GetServerUsersQueryResponse> {
        if (isUserInServer(request.serverId, request.requestFrom)) {
            val users: List<String> = serverRepository.getServerUsers(request.serverId)
            return if (users.isNotEmpty()) {
                Result.success(GetServerUsersQueryResponse(users))
            } else {
                Result.failure(ServerNotFoundException())
            }
        }
        return Result.failure(ServerNotFoundException())
    }

    override fun getServersFromUser(
        request: GetServersFromUserRequest
    ): Result<GetServersFromUserResponse> {
        if (request.username == request.requestFrom) {
            val servers = serverRepository.getServersFromUser(request.username)
            return Result.success(GetServersFromUserResponse(servers))
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
