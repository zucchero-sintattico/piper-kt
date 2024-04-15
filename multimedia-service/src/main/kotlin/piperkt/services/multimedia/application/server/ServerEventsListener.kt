package piperkt.services.multimedia.application.server

import piperkt.common.EventListener
import piperkt.services.multimedia.application.server.ServerService.Command.*
import piperkt.services.multimedia.domain.server.ServerEvent
import piperkt.services.multimedia.domain.server.ServerId
import piperkt.services.multimedia.domain.user.Username

open class ServerEventsListener(private val serverService: ServerService) :
    EventListener<ServerEvent> {

    override fun handle(event: ServerEvent) {
        when (event) {
            is ServerEvent.ServerCreated -> onServerCreated(event)
            is ServerEvent.ServerDeleted -> onServerDeleted(event)
            is ServerEvent.UserJoinedServer -> onUserJoinedServer(event)
            is ServerEvent.UserLeftServer -> onUserLeftServer(event)
            is ServerEvent.UserKickedFromServer -> onUserKickedFromServer(event)
        }
    }

    private fun onServerCreated(event: ServerEvent.ServerCreated) {
        serverService.createServer(CreateServer(setOf(Username(event.owner))))
    }

    private fun onServerDeleted(event: ServerEvent.ServerDeleted) {
        serverService.deleteServer(DeleteServer(ServerId(event.serverId)))
    }

    private fun onUserJoinedServer(event: ServerEvent.UserJoinedServer) {
        serverService.addServerMember(
            AddServerMember(ServerId(event.serverId), Username(event.username))
        )
    }

    private fun onUserLeftServer(event: ServerEvent.UserLeftServer) {
        serverService.removeServerMember(
            RemoveServerMember(ServerId(event.serverId), Username(event.username))
        )
    }

    private fun onUserKickedFromServer(event: ServerEvent.UserKickedFromServer) {
        serverService.removeServerMember(
            RemoveServerMember(ServerId(event.serverId), Username(event.username))
        )
    }
}
