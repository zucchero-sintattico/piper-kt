package piperkt.services.servers.application.api

import piperkt.services.servers.application.api.command.ServerCommand
import piperkt.services.servers.application.api.query.ServerQuery

interface ServerServiceApi {

    fun createServer(
        request: ServerCommand.CreateServer.Request
    ): Result<ServerCommand.CreateServer.Response>

    fun deleteServer(request: ServerCommand.DeleteServer.Request): Result<Unit>

    fun updateServer(request: ServerCommand.UpdateServer.Request): Result<Unit>

    fun addUserToServer(request: ServerCommand.AddUserToServer.Request): Result<Unit>

    fun removeUserFromServer(request: ServerCommand.RemoveUserFromServer.Request): Result<Unit>

    fun kickUserFromServer(request: ServerCommand.KickUserFromServer.Request): Result<Unit>

    fun getServerUsers(
        request: ServerQuery.GetServerUsers.Request
    ): Result<ServerQuery.GetServerUsers.Response>

    fun getServersFromUser(
        request: ServerQuery.GetServersFromUser.Request
    ): Result<ServerQuery.GetServersFromUser.Response>
}
