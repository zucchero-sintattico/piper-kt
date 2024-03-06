package piperkt.services.servers.application

import piperkt.services.servers.application.api.ServerServiceApi
import piperkt.services.servers.application.exception.ServerNotFoundException
import piperkt.services.servers.application.request.CreateServerRequest
import piperkt.services.servers.domain.Server
import piperkt.services.servers.domain.ServerRepository

open class ServerService(
    private val serverRepository: ServerRepository,
    // private val eventPublisher: EventPublisher,
) : ServerServiceApi {
    override fun getServersFromUser(userId: String): List<Server> =
        serverRepository.findByMember(userId)

    override fun createServer(request: CreateServerRequest): Server {
        return serverRepository.save(request.name, request.description, request.owner)
    }

    override fun deleteServer(serverId: String) {
        return serverRepository.deleteServer(serverId)
    }

    override fun addMemberToServer(serverId: String, userId: String): Server? {
        serverRepository.addServerMember(serverId, userId).let { server ->
            // eventPublisher.publishEvent(ServerJoinedEvent(server))
            return server ?: throw ServerNotFoundException()
        }
    }

    override fun removeMemberToServer(serverId: String, userId: String): Server? {
        serverRepository.removeServerMember(serverId, userId).let { server ->
            // eventPublisher.publishEvent(ServerLeftEvent(server))
            return server ?: throw ServerNotFoundException()
        }
    }

    override fun kickUserFromServer(serverId: String, userId: String): Server? {
        serverRepository.removeServerMember(serverId, userId).let { server ->
            // eventPublisher.publishEvent(UserKickedEvent(server))
            return server ?: throw ServerNotFoundException()
        }
    }
}
