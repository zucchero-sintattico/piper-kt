package piperkt.services.servers.interfaces.web

import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Status
import piperkt.services.commons.domain.id.ServerId
import piperkt.services.servers.application.ServerService
import piperkt.services.servers.application.api.query.ServerQuery
import piperkt.services.servers.interfaces.web.api.ServerHttpControllerApi
import piperkt.services.servers.interfaces.web.api.query.GetServerUsers

@Controller("/servers")
class ServerHttpController(private val serverService: ServerService) : ServerHttpControllerApi {
    @Get("/{serverId}/users")
    @Status(HttpStatus.OK)
    override fun getServerUsers(serverId: String): GetServerUsers.HttpResponse {
        val response =
            serverService
                .getServerUsers(
                    ServerQuery.GetServerUsers.Request(ServerId(serverId), "owner", "owner")
                )
                .getOrThrow()
        return GetServerUsers.HttpResponse(response.users)
    }
}
