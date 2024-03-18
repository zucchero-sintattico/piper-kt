package piperkt.services.multimedia.application.users.listeners

import piperkt.services.multimedia.application.EventListener
import piperkt.services.multimedia.domain.servers.ServerId
import piperkt.services.multimedia.domain.servers.ServerRepository
import piperkt.services.multimedia.domain.users.events.UserKickedFromServer

open class UserKickedFromServerEventListener(private val serverRepository: ServerRepository) :
    EventListener<UserKickedFromServer> {
    override fun handle(event: UserKickedFromServer) {
        serverRepository.findById(ServerId(event.serverId))
        TODO()
    }
}
