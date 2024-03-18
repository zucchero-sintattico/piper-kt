package piperkt.services.multimedia.application.servers.listeners

import piperkt.services.multimedia.application.EventListener
import piperkt.services.multimedia.application.servers.events.ServerCreated
import piperkt.services.multimedia.domain.servers.Server
import piperkt.services.multimedia.domain.servers.ServerId
import piperkt.services.multimedia.domain.servers.ServerRepository
import piperkt.services.multimedia.domain.users.User
import piperkt.services.multimedia.domain.users.Username

open class ServerCreatedEventListener(private val serverRepository: ServerRepository) :
    EventListener<ServerCreated> {
    override fun handle(event: ServerCreated) {
        serverRepository.save(
            Server(ServerId(event.serverId), emptyList(), listOf(User(Username(event.owner))))
        )
    }
}
