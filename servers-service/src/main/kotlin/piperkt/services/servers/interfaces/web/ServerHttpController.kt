package piperkt.services.servers.interfaces.web

import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Status
import piperkt.common.id.ServerId
import piperkt.services.servers.application.ServerService
import piperkt.services.servers.application.api.query.ServerQuery
import piperkt.services.servers.interfaces.web.api.ServerHttpControllerApi
import piperkt.services.servers.interfaces.web.api.servers.CreateServerApi
import piperkt.services.servers.interfaces.web.api.servers.GetServerUsersApi

@Controller("/servers")
class ServerHttpController(private val serverService: ServerService) : ServerHttpControllerApi {
    override fun createServer(@Body request: CreateServerApi.Request): CreateServerApi.Response {
        TODO("Not yet implemented")
    }

    @Get("/{serverId}/users")
    @Status(HttpStatus.OK)
    override fun getServerUsers(@PathVariable serverId: String): GetServerUsersApi.Response {
        val response =
            serverService
                .getServerUsers(
                    ServerQuery.GetServerUsers.Request(ServerId(serverId), "owner", "owner")
                )
                .getOrThrow()
        return GetServerUsersApi.Response(response.users)
    }
}
