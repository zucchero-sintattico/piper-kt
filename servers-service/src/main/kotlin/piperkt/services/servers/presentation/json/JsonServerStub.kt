package piperkt.services.servers.presentation.json

import piperkt.services.servers.presentation.json.request.*
import piperkt.services.servers.presentation.json.response.*

interface JsonServerStub {
    fun createServer(request: CreateServerRequest): CreateServerResponse

    fun updateServer(request: UpdateServerRequest): UpdateServerResponse

    fun deleteServer(request: DeleteServerRequest): DeleteServerResponse

    fun addUserToServer(request: AddUserToServerRequest): AddUserToServerResponse

    fun removeUserFromServer(request: RemoveUserFromServerRequest): RemoveUserFromServerResponse

    fun kickUserFromServer(request: KickUserFromServerRequest): KickUserFromServerResponse

    fun getServerUsers(request: GetServerUsersRequest): GetServerUsersResponse

    fun getServersFromUser(request: GetServersFromUserRequest): GetServersFromUserResponse
}
