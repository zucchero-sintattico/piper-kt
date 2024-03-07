package piperkt.services.servers.application.api

import piperkt.services.servers.application.request.AddMemberToServerRequest
import piperkt.services.servers.application.request.CreateServerRequest
import piperkt.services.servers.application.request.DeleteServerRequest
import piperkt.services.servers.application.request.GetServerFromUserRequest
import piperkt.services.servers.application.request.GetServerMembersRequest
import piperkt.services.servers.application.request.KickUserFromServerRequest
import piperkt.services.servers.application.request.RemoveMemberToServerRequest
import piperkt.services.servers.domain.Server

interface ServerServiceApi {
    fun getServersFromUser(request: GetServerFromUserRequest): List<Server>

    fun createServer(
        request: CreateServerRequest,
    ): Server

    fun deleteServer(
        request: DeleteServerRequest,
    )

    fun addMemberToServer(
        request: AddMemberToServerRequest,
    ): Server?

    fun removeMemberToServer(
        request: RemoveMemberToServerRequest,
    ): Server?

    fun kickUserFromServer(
        request: KickUserFromServerRequest,
    ): Server?

    fun getServerMembers(
        request: GetServerMembersRequest,
    ): List<String>
}
