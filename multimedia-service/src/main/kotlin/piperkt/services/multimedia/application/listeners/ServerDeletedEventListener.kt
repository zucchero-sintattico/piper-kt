package piperkt.services.multimedia.application.listeners

import piperkt.services.multimedia.application.EventListener
import piperkt.services.multimedia.domain.ServerId
import piperkt.services.multimedia.domain.ServerRepository
import piperkt.services.multimedia.domain.events.ServerDeleted

open class ServerDeletedEventListener(private val serverRepository: ServerRepository) :
    EventListener<ServerDeleted> {
    override fun handle(event: ServerDeleted) {
        serverRepository.deleteById(ServerId(event.serverId))
    }
}
