package piperkt.services.multimedia.application.servers

import piperkt.services.multimedia.application.EventListener
import piperkt.services.multimedia.application.servers.events.ServerDeleted
import piperkt.services.multimedia.domain.servers.ServerId
import piperkt.services.multimedia.domain.servers.ServerRepository

open class ServerDeletedEventListener(private val serverRepository: ServerRepository) :
    EventListener<ServerDeleted> {
    override fun handle(event: ServerDeleted) {
        serverRepository.deleteById(ServerId(event.serverId))
    }
}
