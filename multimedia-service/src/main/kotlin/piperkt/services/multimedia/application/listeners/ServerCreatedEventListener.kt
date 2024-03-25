package piperkt.services.multimedia.application.listeners

import piperkt.services.multimedia.application.EventListener
import piperkt.services.multimedia.domain.Server
import piperkt.services.multimedia.domain.ServerRepository
import piperkt.services.multimedia.domain.User
import piperkt.services.multimedia.domain.events.ServerCreated

open class ServerCreatedEventListener(private val serverRepository: ServerRepository) :
    EventListener<ServerCreated> {
    override fun handle(event: ServerCreated) {
        val server = Server.new(User.fromUsername(event.owner))
        serverRepository.save(server)
    }
}
