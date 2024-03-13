package piperkt.services.multimedia.application.servers

import piperkt.services.multimedia.application.servers.events.ServerCreated
import piperkt.services.multimedia.application.servers.events.ServerDeleted
import piperkt.services.multimedia.domain.servers.Server
import piperkt.services.multimedia.domain.servers.ServerId
import piperkt.services.multimedia.domain.servers.ServerRepository
import piperkt.services.multimedia.domain.users.User
import piperkt.services.multimedia.domain.users.UserId

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
