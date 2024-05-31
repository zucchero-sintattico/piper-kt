package piperkt.services.servers.interfaces.web.api

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
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
    )
    @Post("/servers/")
    fun createServer(
        @Body request: ServerApi.CreateServerApi.Request,
        principal: Principal
    ): ServerApi.CreateServerApi.Response

    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Users found successfully"),
        ApiResponse(responseCode = "400", description = "Bad request"),
        ApiResponse(responseCode = "401", description = "Unauthorized"),
        ApiResponse(responseCode = "404", description = "Server not found"),
    )
    @Get("/servers/{serverId}/users")
    fun getServerUsers(
        @PathVariable serverId: String,
        principal: Principal
    ): ServerApi.GetServerUsersApi.Response

    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Servers found successfully"),
        ApiResponse(responseCode = "400", description = "Bad request"),
        ApiResponse(responseCode = "401", description = "Unauthorized"),
    )
    @Get("/servers/")
    fun getServersFromUser(principal: Principal): ServerApi.GetServersFromUserApi.Response

    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Server updated successfully"),
        ApiResponse(responseCode = "400", description = "Bad request"),
        ApiResponse(responseCode = "401", description = "Unauthorized"),
        ApiResponse(responseCode = "403", description = "Forbidden"),
        ApiResponse(responseCode = "404", description = "Server not found"),
    )
    @Put("/servers/{serverId}")
    fun updateServer(
        @PathVariable serverId: String,
        @Body request: ServerApi.UpdateServerApi.Request,
        principal: Principal
    ): ServerApi.UpdateServerApi.Response

    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Server deleted successfully"),
        ApiResponse(responseCode = "400", description = "Bad request"),
        ApiResponse(responseCode = "401", description = "Unauthorized"),
        ApiResponse(responseCode = "403", description = "Forbidden"),
        ApiResponse(responseCode = "404", description = "Server not found"),
    )
    @Delete("/servers/{serverId}")
    fun deleteServer(
        @PathVariable serverId: String,
        principal: Principal
    ): ServerApi.DeleteServerApi.Response

    @ApiResponses(
        ApiResponse(responseCode = "200", description = "User added to server successfully"),
        ApiResponse(responseCode = "400", description = "Bad request"),
        ApiResponse(responseCode = "401", description = "Unauthorized"),
        ApiResponse(responseCode = "404", description = "Server not found"),
    )
    @Post("/servers/{serverId}/users")
    fun addUserToServer(
        @PathVariable serverId: String,
        principal: Principal
    ): ServerApi.AddUserToServerApi.Response

    @ApiResponses(
        ApiResponse(responseCode = "200", description = "User removed from server successfully"),
        ApiResponse(responseCode = "400", description = "Bad request"),
        ApiResponse(responseCode = "401", description = "Unauthorized"),
        ApiResponse(responseCode = "403", description = "Forbidden"),
        ApiResponse(responseCode = "404", description = "Server not found"),
    )
    @Delete("/servers/{serverId}/users")
    fun removeUserFromServer(
        @PathVariable serverId: String,
        principal: Principal
    ): ServerApi.RemoveUserFromServerApi.Response

    @ApiResponses(
        ApiResponse(responseCode = "200", description = "User kicked from server successfully"),
        ApiResponse(responseCode = "400", description = "Bad request"),
        ApiResponse(responseCode = "401", description = "Unauthorized"),
        ApiResponse(responseCode = "403", description = "Forbidden"),
        ApiResponse(responseCode = "404", description = "Server not found"),
    )
    @Delete("/servers/{serverId}/users/{username}")
    fun kickUserFromServer(
        @PathVariable serverId: String,
        @PathVariable username: String,
        principal: Principal
    ): ServerApi.KickUserFromServerApi.Response
}
