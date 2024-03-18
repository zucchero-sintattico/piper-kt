package piperkt.services.multimedia.application.users.listeners

import piperkt.services.multimedia.application.EventListener
import piperkt.services.multimedia.domain.servers.ServerId
import piperkt.services.multimedia.domain.servers.ServerRepository
import piperkt.services.multimedia.domain.users.events.UserLeftServer

open class UserLeftServerEventListener(private val serverRepository: ServerRepository) :
    EventListener<UserLeftServer> {
    override fun handle(event: UserLeftServer) {
        serverRepository.findById(ServerId(event.serverId))
        TODO()
    }
}
