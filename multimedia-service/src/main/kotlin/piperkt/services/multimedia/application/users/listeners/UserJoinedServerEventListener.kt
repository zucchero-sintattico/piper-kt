package piperkt.services.multimedia.application.users.listeners

import piperkt.services.multimedia.application.EventListener
import piperkt.services.multimedia.domain.servers.ServerId
import piperkt.services.multimedia.domain.servers.ServerRepository
import piperkt.services.multimedia.domain.users.events.UserJoinedServer

open class UserJoinedServerEventListener(private val serverRepository: ServerRepository) :
    EventListener<UserJoinedServer> {
    override fun handle(event: UserJoinedServer) {
        serverRepository.findById(ServerId(event.serverId))
        TODO()
    }
}
