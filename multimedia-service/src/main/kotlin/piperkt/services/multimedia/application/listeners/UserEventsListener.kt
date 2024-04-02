package piperkt.services.multimedia.application.listeners

import piperkt.services.multimedia.domain.events.EventListener
import piperkt.services.multimedia.domain.events.UserEvent
import piperkt.services.multimedia.domain.server.ServerId
import piperkt.services.multimedia.domain.server.ServerRepository
import piperkt.services.multimedia.domain.user.UserId

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
        val server = serverRepository.findById(ServerId(event.serverId))!!
        server.addMember(UserId(event.username))
        serverRepository.save(server)
    }

    private fun onUserLeftServer(event: UserEvent.UserLeftServer) {
        val server = serverRepository.findById(ServerId(event.serverId))!!
        server.removeMember(UserId(event.username))
        serverRepository.save(server)
    }

    private fun onUserKickedFromServer(event: UserEvent.UserKickedFromServer) {
        val server = serverRepository.findById(ServerId(event.serverId))!!
        server.removeMember(UserId(event.username))
        serverRepository.save(server)
    }
}
