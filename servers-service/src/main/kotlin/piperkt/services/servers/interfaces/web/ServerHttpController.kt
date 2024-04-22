package piperkt.services.servers.interfaces.web

import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.http.annotation.Status
import java.security.Principal
import piperkt.common.id.ServerId
import piperkt.services.servers.application.ServerService
import piperkt.services.servers.application.api.command.ServerCommand
import piperkt.services.servers.application.api.query.ServerQuery
import piperkt.services.servers.interfaces.web.api.ServerHttpControllerApi
import piperkt.services.servers.interfaces.web.api.dto.ServerDTO
import piperkt.services.servers.interfaces.web.api.servers.ServerApi

@Controller("/servers")
class ServerHttpController(private val serverService: ServerService) : ServerHttpControllerApi {

    @Post("/")
    @Status(HttpStatus.OK)
    override fun createServer(
        request: ServerApi.CreateServerApi.Request,
        principal: Principal
    ): ServerApi.CreateServerApi.Response {
        val response =
            serverService
                .createServer(
                    ServerCommand.CreateServer.Request(
                        name = request.name,
                        description = request.description,
                        requestFrom = principal.name
                    )
                )
                .getOrThrow()
        return ServerApi.CreateServerApi.Response(serverId = response.serverId.value)
    }

    @Get("/{serverId}/users")
    @Status(HttpStatus.OK)
    override fun getServerUsers(
        serverId: String,
        principal: Principal
    ): ServerApi.GetServerUsersApi.Response {
        val response =
            serverService
                .getServerUsers(
                    ServerQuery.GetServerUsers.Request(
                        serverId = ServerId(serverId),
                        requestFrom = principal.name
                    )
                )
                .getOrThrow()
        return ServerApi.GetServerUsersApi.Response(users = response.users)
    }

    @Get("/")
    @Status(HttpStatus.OK)
    override fun getServersFromUser(
        principal: Principal
    ): ServerApi.GetServersFromUserApi.Response {
        val response =
            serverService
                .getServersFromUser(
                    ServerQuery.GetServersFromUser.Request(
                        username = principal.name,
                        requestFrom = principal.name
                    )
                )
                .getOrThrow()
        return ServerApi.GetServersFromUserApi.Response(
            servers = response.servers.map { ServerDTO.fromDomain(it) }
        )
    }

    @Put("/{serverId}")
    @Status(HttpStatus.OK)
    override fun updateServer(
        serverId: String,
        request: ServerApi.UpdateServerApi.Request,
        principal: Principal
    ): ServerApi.UpdateServerApi.Response {
        serverService
            .updateServer(
                ServerCommand.UpdateServer.Request(
                    serverId = ServerId(serverId),
                    name = request.name,
                    description = request.description,
                    requestFrom = principal.name
                )
            )
            .getOrThrow()
        return ServerApi.UpdateServerApi.Response
    }

    @Delete("/{serverId}")
    override fun deleteServer(
        serverId: String,
        principal: Principal
    ): ServerApi.DeleteServerApi.Response {
        serverService
            .deleteServer(
                ServerCommand.DeleteServer.Request(
                    serverId = ServerId(serverId),
                    requestFrom = principal.name
                )
            )
            .getOrThrow()
        return ServerApi.DeleteServerApi.Response
    }
}
