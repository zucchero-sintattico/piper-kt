package piperkt.services.servers.application

import piperkt.services.commons.domain.id.ServerId
import piperkt.services.servers.application.api.ServerServiceApi
import piperkt.services.servers.application.exception.ServerNotFoundException
import piperkt.services.servers.domain.Server
import piperkt.services.servers.domain.ServerRepository

open class ServerService(
    private val serverRepository: ServerRepository,
    // private val eventPublisher: EventPublisher,
) : ServerServiceApi {
    override fun getServersFromUser(username: String): List<Server> =
        serverRepository.getServersFromUser(username)

    override fun createServer(name: String, description: String, ownerUsername: String): Server {
        return serverRepository.save(name, description, ownerUsername)
    }

    override fun deleteServer(serverId: ServerId): Boolean {
        return serverRepository.deleteServer(serverId)
    }

    override fun updateServer(serverId: ServerId, name: String?, description: String?): Server? {
        return serverRepository.updateServer(serverId, name, description)
    }

    override fun addUserToServer(serverId: ServerId, username: String): Server? {
        serverRepository.addUserToServer(serverId, username).let {
            // eventPublisher.publishEvent(ServerJoinedEvent(server))
            return it ?: throw ServerNotFoundException()
        }
    }

    override fun removeUserFromServer(serverId: ServerId, username: String): Server? {
        serverRepository.removeUserFromServer(serverId, username).let { server ->
            // eventPublisher.publishEvent(ServerLeftEvent(server))
            return server ?: throw ServerNotFoundException()
        }
    }

    override fun kickUserFromServer(serverId: ServerId, username: String): Server? {
        serverRepository.removeUserFromServer(serverId, username).let { server ->
            // eventPublisher.publishEvent(ServerLeftEvent(server))
            return server ?: throw ServerNotFoundException()
        }
    }

    override fun getServerUsers(serverId: ServerId): List<String> {
        serverRepository.getServerUsers(serverId).let { members ->
            if (members.isEmpty()) throw ServerNotFoundException()
            return members
        }
    }
}
