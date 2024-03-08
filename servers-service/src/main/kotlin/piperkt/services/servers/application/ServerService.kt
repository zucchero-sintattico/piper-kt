package piperkt.services.servers.application

import piperkt.services.servers.application.api.ServerServiceApi
import piperkt.services.servers.application.api.command.AddUserToServerRequest
import piperkt.services.servers.application.api.command.CommandResponse
import piperkt.services.servers.application.api.command.CreateServerRequest
import piperkt.services.servers.application.api.command.DeleteServerRequest
import piperkt.services.servers.application.api.command.KickUserFromServerRequest
import piperkt.services.servers.application.api.command.RemoveUserFromServerRequest
import piperkt.services.servers.application.api.command.UpdateServerRequest
import piperkt.services.servers.application.api.query.GetServerUsersRequest
import piperkt.services.servers.application.api.query.GetServerUsersResponse
import piperkt.services.servers.application.api.query.GetServersFromUserRequest
import piperkt.services.servers.application.api.query.GetServersFromUserResponse
import piperkt.services.servers.domain.ServerRepository

open class ServerService(
    private val serverRepository: ServerRepository,
    // private val eventPublisher: EventPublisher,
) : ServerServiceApi {
    override fun createServer(request: CreateServerRequest): CommandResponse {
        serverRepository.save(request.name, request.description, request.owner)
        return CommandResponse(true)
    }

    override fun deleteServer(request: DeleteServerRequest): CommandResponse {
        return CommandResponse(success = serverRepository.deleteServer(request.serverId))
    }

    override fun updateServer(request: UpdateServerRequest): CommandResponse {
        return CommandResponse(
            success =
                serverRepository.updateServer(
                    request.serverId,
                    request.name,
                    request.description
                ) != null
        )
    }

    override fun addUserToServer(request: AddUserToServerRequest): CommandResponse {
        return CommandResponse(
            success = serverRepository.addUserToServer(request.serverId, request.username) != null
        )
    }

    override fun removeUserFromServer(request: RemoveUserFromServerRequest): CommandResponse {
        return CommandResponse(
            success =
                serverRepository.removeUserFromServer(request.serverId, request.username) != null
        )
    }

    override fun kickUserFromServer(request: KickUserFromServerRequest): CommandResponse {
        return CommandResponse(
            success =
                serverRepository.removeUserFromServer(request.serverId, request.username) != null
        )
    }

    override fun getServerUsers(request: GetServerUsersRequest): GetServerUsersResponse {
        return GetServerUsersResponse(
            success = true,
            users = serverRepository.getServerUsers(request.serverId)
        )
    }

    override fun getServersFromUser(
        request: GetServersFromUserRequest
    ): GetServersFromUserResponse {
        return GetServersFromUserResponse(
            servers = serverRepository.getServersFromUser(request.username)
        )
    }
}
