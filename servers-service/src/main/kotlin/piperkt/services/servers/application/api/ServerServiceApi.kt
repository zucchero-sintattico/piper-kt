package piperkt.services.servers.application.api

import piperkt.services.servers.application.api.command.ServerCommand
import piperkt.services.servers.application.api.query.servers.GetServerUsersQueryResponse
import piperkt.services.servers.application.api.query.servers.GetServerUsersRequest
import piperkt.services.servers.application.api.query.servers.GetServersFromUserRequest
import piperkt.services.servers.application.api.query.servers.GetServersFromUserResponse

interface ServerServiceApi {

    fun createServer(
        request: ServerCommand.CreateServer.Request
    ): Result<ServerCommand.CreateServer.Response>

    fun deleteServer(request: ServerCommand.DeleteServer.Request): Result<Unit>

    fun updateServer(request: ServerCommand.UpdateServer.Request): Result<Unit>

    fun addUserToServer(request: ServerCommand.AddUserToServer.Request): Result<Unit>

    fun removeUserFromServer(request: ServerCommand.RemoveUserFromServer.Request): Result<Unit>

    fun kickUserFromServer(request: ServerCommand.KickUserFromServer.Request): Result<Unit>

    fun getServerUsers(request: GetServerUsersRequest): Result<GetServerUsersQueryResponse>

    fun getServersFromUser(request: GetServersFromUserRequest): Result<GetServersFromUserResponse>
}
