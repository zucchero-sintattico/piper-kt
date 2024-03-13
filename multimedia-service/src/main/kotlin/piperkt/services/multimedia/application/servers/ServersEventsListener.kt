package piperkt.services.multimedia.application.servers

import piperkt.services.multimedia.domain.servers.Server
import piperkt.services.multimedia.domain.servers.ServerId
import piperkt.services.multimedia.domain.servers.ServerRepository
import piperkt.services.multimedia.domain.users.User
import piperkt.services.multimedia.domain.users.UserId

data class ServerCreated(val serverId: String, val owner: String)

data class ServerDeleted(val serverId: String)

open class ServersEventsListener(private val serverRepository: ServerRepository) {
    fun onServerCreated(event: ServerCreated) {
        serverRepository.save(
            Server(ServerId(event.serverId), emptyList(), listOf(User(UserId(event.owner))))
        )
    }

    fun onServerDeleted(event: ServerDeleted) {
        serverRepository.deleteById(ServerId(event.serverId))
    }
}
