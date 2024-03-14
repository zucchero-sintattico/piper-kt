package piperkt.services.servers.application.api

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

interface ServerServiceApi {
    // Commands

    fun createServer(request: CreateServerRequest): Result<Unit>

    fun deleteServer(request: DeleteServerRequest): Result<Unit>

    fun updateServer(request: UpdateServerRequest): Result<Unit>

    fun addUserToServer(request: AddUserToServerRequest): Result<Unit>

    fun removeUserFromServer(request: RemoveUserFromServerRequest): Result<Unit>

    fun kickUserFromServer(request: KickUserFromServerRequest): Result<Unit>

    // Queries

    fun getServerUsers(request: GetServerUsersRequest): Result<GetServerUsersResponse>

    fun getServersFromUser(request: GetServersFromUserRequest): Result<GetServersFromUserResponse>
}
