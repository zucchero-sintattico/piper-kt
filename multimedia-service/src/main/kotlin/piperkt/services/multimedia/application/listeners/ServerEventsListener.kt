package piperkt.services.multimedia.application.listeners

import piperkt.common.EventListener
import piperkt.services.multimedia.application.orThrow
import piperkt.services.multimedia.domain.server.Server
import piperkt.services.multimedia.domain.server.ServerErrors
import piperkt.services.multimedia.domain.server.ServerEvent
import piperkt.services.multimedia.domain.server.ServerId
import piperkt.services.multimedia.domain.server.ServerRepository
import piperkt.services.multimedia.domain.user.Username

open class ServerEventsListener(private val serverRepository: ServerRepository) :
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
        val server = Server(members = listOf(Username(event.owner)))
        serverRepository.save(server)
    }

    private fun onServerDeleted(event: ServerEvent.ServerDeleted) {
        serverRepository.deleteById(ServerId(event.serverId))
    }

    private fun onUserJoinedServer(event: ServerEvent.UserJoinedServer) {
        val server =
            serverRepository
                .findById(ServerId(event.serverId))
                .orThrow(ServerErrors.ServerNotFound(ServerId(event.serverId)))
        server.addMember(Username(event.username))
        serverRepository.save(server)
    }

    private fun onUserLeftServer(event: ServerEvent.UserLeftServer) {
        val server =
            serverRepository
                .findById(ServerId(event.serverId))
                .orThrow(ServerErrors.ServerNotFound(ServerId(event.serverId)))
        server.removeMember(Username(event.username))
        serverRepository.save(server)
    }

    private fun onUserKickedFromServer(event: ServerEvent.UserKickedFromServer) {
        val server =
            serverRepository
                .findById(ServerId(event.serverId))
                .orThrow(ServerErrors.ServerNotFound(ServerId(event.serverId)))
        server.removeMember(Username(event.username))
        serverRepository.save(server)
    }
}
