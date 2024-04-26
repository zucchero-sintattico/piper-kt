package piperkt.services.servers.interfaces.web.api

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.PathVariable
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import java.security.Principal
import piperkt.services.servers.interfaces.web.api.servers.ServerApi

@Secured(SecurityRule.IS_AUTHENTICATED)
interface ServerHttpControllerApi {

    fun createServer(
        @Body request: ServerApi.CreateServerApi.Request,
        principal: Principal
    ): ServerApi.CreateServerApi.Response

    fun getServerUsers(
        @PathVariable serverId: String,
        principal: Principal
    ): ServerApi.GetServerUsersApi.Response

    fun getServersFromUser(principal: Principal): ServerApi.GetServersFromUserApi.Response

    fun updateServer(
        @PathVariable serverId: String,
        @Body request: ServerApi.UpdateServerApi.Request,
        principal: Principal
    ): ServerApi.UpdateServerApi.Response

    fun deleteServer(
        @PathVariable serverId: String,
        principal: Principal
    ): ServerApi.DeleteServerApi.Response

    fun addUserToServer(
        @PathVariable serverId: String,
        @Body request: ServerApi.AddUserToServerApi.Request,
        principal: Principal
    ): ServerApi.AddUserToServerApi.Response

    fun removeUserFromServer(
        @PathVariable serverId: String,
        @Body request: ServerApi.RemoveUserFromServerApi.Request,
        principal: Principal
    ): ServerApi.RemoveUserFromServerApi.Response

    fun kickUserFromServer(
        @PathVariable serverId: String,
        @Body request: ServerApi.KickUserFromServerApi.Request,
        principal: Principal
    ): ServerApi.KickUserFromServerApi.Response
}
