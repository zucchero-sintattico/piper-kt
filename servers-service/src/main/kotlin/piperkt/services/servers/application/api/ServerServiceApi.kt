package piperkt.services.servers.application.api

import piperkt.services.servers.application.api.command.ServerCommand
import piperkt.services.servers.application.api.query.ServerQuery

interface ServerServiceApi {

    fun createServer(
        request: ServerCommand.CreateServer.Request
    ): Result<ServerCommand.CreateServer.Response>

    fun deleteServer(
        request: ServerCommand.DeleteServer.Request
    ): Result<ServerCommand.DeleteServer.Response>

    fun updateServer(
        request: ServerCommand.UpdateServer.Request
    ): Result<ServerCommand.UpdateServer.Response>

    fun addUserToServer(
        request: ServerCommand.AddUserToServer.Request
    ): Result<ServerCommand.AddUserToServer.Response>

    fun removeUserFromServer(
        request: ServerCommand.RemoveUserFromServer.Request
    ): Result<ServerCommand.RemoveUserFromServer.Response>

    fun kickUserFromServer(
        request: ServerCommand.KickUserFromServer.Request
    ): Result<ServerCommand.KickUserFromServer.Response>

    fun getServerUsers(
        request: ServerQuery.GetServerUsers.Request
    ): Result<ServerQuery.GetServerUsers.Response>

    fun getServersFromUser(
        request: ServerQuery.GetServersFromUser.Request
    ): Result<ServerQuery.GetServersFromUser.Response>
}
