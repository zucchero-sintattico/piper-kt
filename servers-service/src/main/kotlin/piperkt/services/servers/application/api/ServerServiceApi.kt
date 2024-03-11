package piperkt.services.servers.application.api

import piperkt.services.servers.application.api.command.CommandResponse
import piperkt.services.servers.application.api.command.servers.AddUserToServerRequest
import piperkt.services.servers.application.api.command.servers.CreateServerRequest
import piperkt.services.servers.application.api.command.servers.DeleteServerRequest
import piperkt.services.servers.application.api.command.servers.KickUserFromServerRequest
import piperkt.services.servers.application.api.command.servers.RemoveUserFromServerRequest
import piperkt.services.servers.application.api.command.servers.UpdateServerRequest
import piperkt.services.servers.application.api.query.servers.GetServerUsersRequest
import piperkt.services.servers.application.api.query.servers.GetServerUsersResponse
import piperkt.services.servers.application.api.query.servers.GetServersFromUserRequest
import piperkt.services.servers.application.api.query.servers.GetServersFromUserResponse

interface ServerServiceApi {
    // Commands

    fun createServer(request: CreateServerRequest): CommandResponse

    fun deleteServer(request: DeleteServerRequest): CommandResponse

    fun updateServer(request: UpdateServerRequest): CommandResponse

    fun addUserToServer(request: AddUserToServerRequest): CommandResponse

    fun removeUserFromServer(request: RemoveUserFromServerRequest): CommandResponse

    fun kickUserFromServer(request: KickUserFromServerRequest): CommandResponse

    // Queries

    fun getServerUsers(request: GetServerUsersRequest): GetServerUsersResponse

    fun getServersFromUser(request: GetServersFromUserRequest): GetServersFromUserResponse
}
