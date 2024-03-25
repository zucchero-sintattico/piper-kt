package piperkt.services.multimedia.application.listeners

import piperkt.services.multimedia.application.EventListener
import piperkt.services.multimedia.domain.ServerId
import piperkt.services.multimedia.domain.ServerRepository
import piperkt.services.multimedia.domain.User
import piperkt.services.multimedia.domain.events.UserLeftServer

open class UserLeftServerEventListener(private val serverRepository: ServerRepository) :
    EventListener<UserLeftServer> {
    override fun handle(event: UserLeftServer) {
        serverRepository.removeUser(ServerId(event.serverId), User.fromUsername(event.username))
    }
}
