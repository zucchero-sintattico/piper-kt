package piperkt.services.servers.presentation.json

import piperkt.services.commons.domain.id.ServerId
import piperkt.services.servers.application.ServerService
import piperkt.services.servers.presentation.json.request.*
import piperkt.services.servers.presentation.json.response.*

open class JsonServerStubImpl(private val serverService: ServerService) : JsonServerStub {
    override fun createServer(request: CreateServerRequest): CreateServerResponse {
        return serverService.createServer(request.name, request.description, request.owner).let {
            CreateServerResponse(it.name, it.description)
        }
    }

    override fun updateServer(request: UpdateServerRequest): UpdateServerResponse {
        return serverService
            .updateServer(ServerId(request.serverId), request.name, request.description)
            .let {
                if (it != null) {
                    UpdateServerResponse(it.id.value, it.name, it.description)
                } else {
                    UpdateServerResponse(null, null, null)
                }
            }
    }

    override fun deleteServer(request: DeleteServerRequest): DeleteServerResponse {
        return DeleteServerResponse(serverService.deleteServer(ServerId(request.serverId)))
    }

    override fun addUserToServer(request: AddUserToServerRequest): AddUserToServerResponse {
        return serverService.addUserToServer(ServerId(request.serverId), request.username).let {
            if (it != null) {
                AddUserToServerResponse(true)
            } else {
                AddUserToServerResponse(false)
            }
        }
    }

    override fun removeUserFromServer(
        request: RemoveUserFromServerRequest
    ): RemoveUserFromServerResponse {
        return serverService
            .removeUserFromServer(ServerId(request.serverId), request.username)
            .let {
                if (it != null) {
                    RemoveUserFromServerResponse(true)
                } else {
                    RemoveUserFromServerResponse(false)
                }
            }
    }

    override fun kickUserFromServer(
        request: KickUserFromServerRequest
    ): KickUserFromServerResponse {
        return serverService.kickUserFromServer(ServerId(request.serverId), request.username).let {
            if (it != null) {
                KickUserFromServerResponse(true)
            } else {
                KickUserFromServerResponse(false)
            }
        }
    }

    override fun getServerUsers(request: GetServerUsersRequest): GetServerUsersResponse {
        return serverService.getServerUsers(ServerId(request.serverId)).let {
            if (it.isNotEmpty()) {
                GetServerUsersResponse(true, it)
            } else {
                GetServerUsersResponse(false, emptyList())
            }
        }
    }

    override fun getServersFromUser(
        request: GetServersFromUserRequest
    ): GetServersFromUserResponse {
        TODO("Not yet implemented, need to know how to serialize Server Entity")
    }
}
