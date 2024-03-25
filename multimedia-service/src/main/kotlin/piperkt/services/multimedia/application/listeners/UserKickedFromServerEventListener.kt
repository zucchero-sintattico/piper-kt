package piperkt.services.multimedia.application.listeners

import piperkt.services.multimedia.application.EventListener
import piperkt.services.multimedia.domain.ServerId
import piperkt.services.multimedia.domain.ServerRepository
import piperkt.services.multimedia.domain.User
import piperkt.services.multimedia.domain.events.UserKickedFromServer

open class UserKickedFromServerEventListener(private val serverRepository: ServerRepository) :
    EventListener<UserKickedFromServer> {
    override fun handle(event: UserKickedFromServer) {
        serverRepository.removeUser(ServerId(event.serverId), User.fromUsername(event.username))
    }
}
