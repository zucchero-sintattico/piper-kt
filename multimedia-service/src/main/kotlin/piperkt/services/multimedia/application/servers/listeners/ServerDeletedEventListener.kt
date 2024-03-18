package piperkt.services.multimedia.application.servers.listeners

import piperkt.services.multimedia.application.EventListener
import piperkt.services.multimedia.domain.servers.ServerId
import piperkt.services.multimedia.domain.servers.ServerRepository
import piperkt.services.multimedia.domain.servers.events.ServerDeleted

open class ServerDeletedEventListener(private val serverRepository: ServerRepository) :
    EventListener<ServerDeleted> {
    override fun handle(event: ServerDeleted) {
        serverRepository.deleteById(ServerId(event.serverId))
    }
}
