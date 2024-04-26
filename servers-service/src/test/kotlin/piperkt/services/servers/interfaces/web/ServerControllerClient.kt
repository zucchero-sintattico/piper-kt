package piperkt.services.servers.interfaces.web

import io.micronaut.http.HttpHeaders
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.http.client.annotation.Client
import io.micronaut.retry.annotation.Retryable
import piperkt.services.servers.interfaces.web.api.servers.ServerApi

@Client("/servers")
@Retryable
interface ServerControllerClient {

    @Get("/")
    fun getServersFromUser(
        @Header(HttpHeaders.AUTHORIZATION) authorization: String = authOf("user")
    ): HttpResponse<ServerApi.GetServersFromUserApi.Response>

    @Get("/{serverId}/users")
    fun getServerUsers(
        @PathVariable serverId: String,
        @Header(HttpHeaders.AUTHORIZATION) authorization: String = authOf("user")
    ): HttpResponse<ServerApi.GetServerUsersApi.Response>

    @Post("/")
    fun createServer(
        @Body request: ServerApi.CreateServerApi.Request,
        @Header(HttpHeaders.AUTHORIZATION) authorization: String = authOf("user")
    ): HttpResponse<ServerApi.CreateServerApi.Response>

    @Put("/{serverId}/")
    fun updateServer(
        @PathVariable serverId: String,
        @Body request: ServerApi.UpdateServerApi.Request,
        @Header(HttpHeaders.AUTHORIZATION) authorization: String = authOf("user")
    ): HttpResponse<ServerApi.UpdateServerApi.Response>

    @Delete("/{serverId}/")
    fun deleteServer(
        @PathVariable serverId: String,
        @Header(HttpHeaders.AUTHORIZATION) authorization: String = authOf("user")
    ): HttpResponse<ServerApi.DeleteServerApi.Response>

    @Post("/{serverId}/users/")
    fun addUserToServer(
        @PathVariable serverId: String,
        @Header(HttpHeaders.AUTHORIZATION) authorization: String
    ): HttpResponse<ServerApi.AddUserToServerApi.Response>

    @Delete("/{serverId}/users/")
    fun removeUserFromServer(
        @PathVariable serverId: String,
        @Header(HttpHeaders.AUTHORIZATION) authorization: String = authOf("user")
    ): HttpResponse<ServerApi.RemoveUserFromServerApi.Response>

    @Delete("/{serverId}/users/{username}")
    fun kickUserFromServer(
        @PathVariable serverId: String,
        @PathVariable username: String,
        @Header(HttpHeaders.AUTHORIZATION) authorization: String = authOf("user")
    ): HttpResponse<ServerApi.KickUserFromServerApi.Response>
}

fun authOf(username: String): String {
    val req = HttpRequest.GET<Any>("/").basicAuth(username, "")
    return req.headers.get(HttpHeaders.AUTHORIZATION)!!
}
