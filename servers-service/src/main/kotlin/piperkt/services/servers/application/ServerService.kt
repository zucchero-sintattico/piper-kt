package piperkt.services.servers.application

import piperkt.services.commons.domain.id.ServerId
import piperkt.services.servers.application.api.ServerServiceApi
import piperkt.services.servers.application.api.command.AddUserToServerRequest
import piperkt.services.servers.application.api.command.CreateServerRequest
import piperkt.services.servers.application.api.command.DeleteServerRequest
import piperkt.services.servers.application.api.command.KickUserFromServerRequest
import piperkt.services.servers.application.api.command.RemoveUserFromServerRequest
import piperkt.services.servers.application.api.command.UpdateServerRequest
import piperkt.services.servers.application.api.query.servers.GetServerUsersRequest
import piperkt.services.servers.application.api.query.servers.GetServerUsersResponse
import piperkt.services.servers.application.api.query.servers.GetServersFromUserRequest
import piperkt.services.servers.application.api.query.servers.GetServersFromUserResponse
import piperkt.services.servers.application.exceptions.ServerOrChannelNotFoundException
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
            return when (success) {
                true -> Result.success(Unit)
                false -> Result.failure(ServerOrChannelNotFoundException())
            }
        } else {
            return Result.failure(UserNotHasPermissionsException())
        }
    }

    override fun updateServer(request: UpdateServerRequest): Result<Unit> {
        if (isUserAdmin(request.serverId, request.requestFrom)) {
            val server =
                serverRepository.updateServer(request.serverId, request.name, request.description)
            return when (server != null) {
                true -> Result.success(Unit)
                false -> Result.failure(ServerOrChannelNotFoundException())
            }
        } else {
            return Result.failure(UserNotHasPermissionsException())
        }
    }

    override fun addUserToServer(request: AddUserToServerRequest): Result<Unit> {
        val server = serverRepository.addUserToServer(request.serverId, request.username)
        return when (server != null) {
            true -> Result.success(Unit)
            false -> Result.failure(ServerOrChannelNotFoundException())
        }
    }

    override fun removeUserFromServer(request: RemoveUserFromServerRequest): Result<Unit> {
        val server = serverRepository.removeUserFromServer(request.serverId, request.username)
        return when (server != null) {
            true -> Result.success(Unit)
            false -> Result.failure(ServerOrChannelNotFoundException())
        }
    }

    override fun kickUserFromServer(request: KickUserFromServerRequest): Result<Unit> {
        if (isUserAdmin(request.serverId, request.requestFrom)) {
            val server = serverRepository.removeUserFromServer(request.serverId, request.username)
            return when (server != null) {
                true -> Result.success(Unit)
                false -> Result.failure(ServerOrChannelNotFoundException())
            }
        }
        return Result.failure(UserNotHasPermissionsException())
    }

    override fun getServerUsers(request: GetServerUsersRequest): Result<GetServerUsersResponse> {
        if (isUserInServer(request.serverId, request.requestFrom)) {
            val users: List<String> = serverRepository.getServerUsers(request.serverId)
            return when (users.isNotEmpty()) {
                true -> Result.success(GetServerUsersResponse(users))
                false -> Result.failure(ServerOrChannelNotFoundException())
            }
        }
        return Result.failure(ServerOrChannelNotFoundException())
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
