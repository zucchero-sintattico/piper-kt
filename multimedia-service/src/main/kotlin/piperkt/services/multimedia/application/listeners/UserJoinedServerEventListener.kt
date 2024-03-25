package piperkt.services.multimedia.application.listeners

import piperkt.services.multimedia.application.EventListener
import piperkt.services.multimedia.domain.ServerId
import piperkt.services.multimedia.domain.ServerRepository
import piperkt.services.multimedia.domain.User
import piperkt.services.multimedia.domain.events.UserJoinedServer

open class UserJoinedServerEventListener(private val serverRepository: ServerRepository) :
    EventListener<UserJoinedServer> {
    override fun handle(event: UserJoinedServer) {
        serverRepository.addUser(ServerId(event.serverId), User.fromUsername(event.username))
    }
}
