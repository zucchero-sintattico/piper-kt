package piperkt.services.servers.presentation.json

import piperkt.services.commons.domain.id.ServerId
import piperkt.services.servers.application.ServerService
import piperkt.services.servers.presentation.json.request.CreateServerRequest
import piperkt.services.servers.presentation.json.request.DeleteServerRequest
import piperkt.services.servers.presentation.json.request.UpdateServerRequest
import piperkt.services.servers.presentation.json.request.AddUserToServerRequest
import piperkt.services.servers.presentation.json.request.RemoveUserFromServerRequest
import piperkt.services.servers.presentation.json.request.KickUserFromServerRequest
import piperkt.services.servers.presentation.json.request.GetServerUsersRequest
import piperkt.services.servers.presentation.json.request.GetServersFromUserRequest
import piperkt.services.servers.presentation.json.response.CreateServerResponse
import piperkt.services.servers.presentation.json.response.DeleteServerResponse
import piperkt.services.servers.presentation.json.response.UpdateServerResponse
import piperkt.services.servers.presentation.json.response.AddUserToServerResponse
import piperkt.services.servers.presentation.json.response.RemoveUserFromServerResponse
import piperkt.services.servers.presentation.json.response.KickUserFromServerResponse
import piperkt.services.servers.presentation.json.response.GetServerUsersResponse
import piperkt.services.servers.presentation.json.response.GetServersFromUserResponse

open class JsonServerStubImpl(private val serverService: ServerService) : JsonServerStub {
    override fun createServer(request: CreateServerRequest): CreateServerResponse {
        return serverService.createServer(request.name, request.description, request.owner).let {
            CreateServerResponse(true)
        }
    }

    override fun updateServer(request: UpdateServerRequest): UpdateServerResponse {
        return serverService
            .updateServer(ServerId(request.serverId), request.name, request.description)
            .let {
                UpdateServerResponse(it != null)
            }
    }

    override fun deleteServer(request: DeleteServerRequest): DeleteServerResponse {
        return DeleteServerResponse(serverService.deleteServer(ServerId(request.serverId)))
    }

    override fun addUserToServer(request: AddUserToServerRequest): AddUserToServerResponse {
        return serverService.addUserToServer(ServerId(request.serverId), request.username).let {
            AddUserToServerResponse(it != null)
        }
    }

    override fun removeUserFromServer(
        request: RemoveUserFromServerRequest
    ): RemoveUserFromServerResponse {
        return serverService
            .removeUserFromServer(ServerId(request.serverId), request.username)
            .let {
                RemoveUserFromServerResponse(it != null)
            }
    }

    override fun kickUserFromServer(
        request: KickUserFromServerRequest
    ): KickUserFromServerResponse {
        return serverService.kickUserFromServer(ServerId(request.serverId), request.username).let {
            KickUserFromServerResponse(it != null)
        }
    }

    override fun getServerUsers(request: GetServerUsersRequest): GetServerUsersResponse {
        return GetServerUsersResponse(true, serverService.getServerUsers(ServerId(request.serverId)))
    }

    override fun getServersFromUser(
        request: GetServersFromUserRequest
    ): GetServersFromUserResponse {
        return GetServersFromUserResponse(serverService.getServersFromUser(request.username).map {
            ServerJson(
                it.id.value,
                it.name,
                it.description,
                it.owner,
                it.users
            )
        })
    }
}
