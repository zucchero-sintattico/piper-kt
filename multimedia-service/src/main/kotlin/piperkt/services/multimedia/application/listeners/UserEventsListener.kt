package piperkt.services.multimedia.application.listeners

import piperkt.services.multimedia.application.EventListener
import piperkt.services.multimedia.domain.ServerId
import piperkt.services.multimedia.domain.ServerRepository
import piperkt.services.multimedia.domain.User
import piperkt.services.multimedia.domain.events.UserEvent

open class UserEventsListener(private val serverRepository: ServerRepository) :
    EventListener<UserEvent> {
    override fun handle(event: UserEvent) {
        when (event) {
            is UserEvent.UserJoinedServer -> onUserJoinedServer(event)
            is UserEvent.UserLeftServer -> onUserLeftServer(event)
            is UserEvent.UserKickedFromServer -> onUserKickedFromServer(event)
        }
    }

    private fun onUserJoinedServer(event: UserEvent.UserJoinedServer) {
        serverRepository.addUser(ServerId(event.serverId), User.fromUsername(event.username))
    }

    private fun onUserLeftServer(event: UserEvent.UserLeftServer) {
        serverRepository.removeUser(ServerId(event.serverId), User.fromUsername(event.username))
    }

    private fun onUserKickedFromServer(event: UserEvent.UserKickedFromServer) {
        serverRepository.removeUser(ServerId(event.serverId), User.fromUsername(event.username))
    }
}
