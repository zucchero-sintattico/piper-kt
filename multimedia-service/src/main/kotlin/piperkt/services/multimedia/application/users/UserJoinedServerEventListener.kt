package piperkt.services.multimedia.application.users

import piperkt.services.multimedia.application.EventListener
import piperkt.services.multimedia.application.users.events.UserJoinedServer
import piperkt.services.multimedia.domain.servers.ServerId
import piperkt.services.multimedia.domain.servers.ServerRepository

open class UserJoinedServerEventListener(private val serverRepository: ServerRepository) :
    EventListener<UserJoinedServer> {
    override fun handle(event: UserJoinedServer) {
        serverRepository.findById(ServerId(event.serverId))
        TODO()
    }
}
