package piperkt.services.servers.application

import piperkt.services.servers.application.api.ServerServiceApi
import piperkt.services.servers.application.request.CreateServerRequest
import piperkt.services.servers.domain.Server
import piperkt.services.servers.domain.ServerRepository

open class ServerService(
    private val serverRepository: ServerRepository,
    // private val eventPublisher: EventPublisher,
) : ServerServiceApi {
    override fun getServersFromUser(userId: String): List<Server> = TODO("Not yet implemented")

    override fun createServer(request: CreateServerRequest): Server {
        return serverRepository.save(request.name, request.description, request.owner)
    }

    override fun deleteServer(serverId: String): Server? {
        TODO("Not yet implemented")
    }

    override fun joinServer(serverId: String, userId: String): Server? {
        TODO("Not yet implemented")
    }

    override fun leaveServer(serverId: String, userId: String): Server? {
        TODO("Not yet implemented")
    }

    override fun kickUserFromServer(serverId: String, userId: String): Server? {
        TODO("Not yet implemented")
    }
}
