package piperkt.services.multimedia.application.listeners

import piperkt.services.multimedia.domain.events.EventListener
import piperkt.services.multimedia.domain.events.ServerEvent
import piperkt.services.multimedia.domain.server.Server
import piperkt.services.multimedia.domain.server.ServerId
import piperkt.services.multimedia.domain.server.ServerRepository
import piperkt.services.multimedia.domain.user.UserId

open class ServerEventsListener(private val serverRepository: ServerRepository) :
    EventListener<ServerEvent> {

    override fun handle(event: ServerEvent) {
        when (event) {
            is ServerEvent.ServerCreated -> onServerCreated(event)
            is ServerEvent.ServerDeleted -> onServerDeleted(event)
        }
    }

    private fun onServerCreated(event: ServerEvent.ServerCreated) {
        val server = Server(members = listOf(UserId(event.owner)))
        serverRepository.save(server)
    }

    private fun onServerDeleted(event: ServerEvent.ServerDeleted) {
        serverRepository.deleteById(ServerId(event.serverId))
    }
}
