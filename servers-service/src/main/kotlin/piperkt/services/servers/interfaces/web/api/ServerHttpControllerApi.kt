package piperkt.services.servers.interfaces.web.api

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.PathVariable
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import java.security.Principal
import piperkt.services.servers.interfaces.web.api.interactions.ServerApi

@Secured(SecurityRule.IS_AUTHENTICATED)
interface ServerHttpControllerApi {

    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Server created successfully"),
        ApiResponse(responseCode = "400", description = "Bad request"),
        ApiResponse(responseCode = "401", description = "Unauthorized"),
        ApiResponse(responseCode = "403", description = "Forbidden"),
    )
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
        principal: Principal
    ): ServerApi.AddUserToServerApi.Response

    fun removeUserFromServer(
        @PathVariable serverId: String,
        principal: Principal
    ): ServerApi.RemoveUserFromServerApi.Response

    fun kickUserFromServer(
        @PathVariable serverId: String,
        @PathVariable username: String,
        principal: Principal
    ): ServerApi.KickUserFromServerApi.Response
}
