package piperkt.services.servers.interfaces.web

import io.micronaut.http.annotation.Controller
import java.security.Principal
import piperkt.services.servers.application.ServerService
import piperkt.services.servers.application.api.command.ServerCommand
import piperkt.services.servers.application.api.query.ServerQuery
import piperkt.services.servers.domain.ServerId
import piperkt.services.servers.interfaces.web.api.ServerHttpControllerApi
import piperkt.services.servers.interfaces.web.api.interactions.ServerApi
import piperkt.services.servers.presentation.ServerDTO

@Controller
class ServerHttpController(private val serverService: ServerService) : ServerHttpControllerApi {

    override fun createServer(
        request: ServerApi.CreateServerApi.Request,
        principal: Principal,
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

    override fun getServerUsers(
        serverId: String,
        principal: Principal,
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

    override fun getServersFromUser(
        principal: Principal,
    ): ServerApi.GetServersFromUserApi.Response {
        val response =
            serverService
                .getServersFromUser(
                    ServerQuery.GetServersFromUser.Request(requestFrom = principal.name)
                )
                .getOrThrow()
        return ServerApi.GetServersFromUserApi.Response(
            servers = response.servers.map { ServerDTO.fromDomain(it) }
        )
    }

    override fun updateServer(
        serverId: String,
        request: ServerApi.UpdateServerApi.Request,
        principal: Principal,
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
        return ServerApi.UpdateServerApi.Response(
            name = request.name,
            description = request.description
        )
    }

    override fun deleteServer(
        serverId: String,
        principal: Principal,
    ): ServerApi.DeleteServerApi.Response {
        serverService
            .deleteServer(
                ServerCommand.DeleteServer.Request(
                    serverId = ServerId(serverId),
                    requestFrom = principal.name
                )
            )
            .getOrThrow()
        return ServerApi.DeleteServerApi.Response(serverId = serverId)
    }

    override fun addUserToServer(
        serverId: String,
        principal: Principal,
    ): ServerApi.AddUserToServerApi.Response {
        serverService
            .addUserToServer(
                ServerCommand.AddUserToServer.Request(
                    serverId = ServerId(serverId),
                    requestFrom = principal.name
                )
            )
            .getOrThrow()
        return ServerApi.AddUserToServerApi.Response(serverId = serverId, username = principal.name)
    }

    override fun removeUserFromServer(
        serverId: String,
        principal: Principal,
    ): ServerApi.RemoveUserFromServerApi.Response {
        serverService
            .removeUserFromServer(
                ServerCommand.RemoveUserFromServer.Request(
                    serverId = ServerId(serverId),
                    requestFrom = principal.name
                )
            )
            .getOrThrow()
        return ServerApi.RemoveUserFromServerApi.Response(
            serverId = serverId,
            username = principal.name
        )
    }

    override fun kickUserFromServer(
        serverId: String,
        username: String,
        principal: Principal,
    ): ServerApi.KickUserFromServerApi.Response {
        serverService
            .kickUserFromServer(
                ServerCommand.KickUserFromServer.Request(
                    serverId = ServerId(serverId),
                    username = username,
                    requestFrom = principal.name
                )
            )
            .getOrThrow()
        return ServerApi.KickUserFromServerApi.Response(serverId = serverId, username = username)
    }
}
