package piperkt.services.servers.interfaces.web

import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Status
import piperkt.services.commons.domain.id.ServerId
import piperkt.services.servers.application.ServerService
import piperkt.services.servers.application.api.query.servers.GetServerUsersQuery
import piperkt.services.servers.interfaces.web.api.ServerHttpControllerApi
import piperkt.services.servers.interfaces.web.api.responses.GetServerUsersHttpResponse

@Controller("/servers")
class ServerHttpController(private val serverService: ServerService) : ServerHttpControllerApi {
    @Get("/{serverId}/users")
    @Status(HttpStatus.OK)
    override fun getServerUsers(serverId: String): GetServerUsersHttpResponse {
        val response =
            serverService
                .getServerUsers(GetServerUsersQuery(ServerId(serverId), "owner"))
                .getOrThrow()
        return GetServerUsersHttpResponse.fromResponse(response)
    }
}
