package piperkt.services.multimedia.application.server

import piperkt.common.events.EventListener
import piperkt.common.utils.orThrow
import piperkt.events.ServerEvent
import piperkt.services.multimedia.application.session.SessionService
import piperkt.services.multimedia.application.session.SessionService.Command.AddAllowedUser
import piperkt.services.multimedia.application.session.SessionService.Command.RemoveAllowedUser
import piperkt.services.multimedia.domain.server.Server
import piperkt.services.multimedia.domain.server.ServerErrors
import piperkt.services.multimedia.domain.server.ServerId
import piperkt.services.multimedia.domain.server.ServerRepository
import piperkt.services.multimedia.domain.user.Username

open class ServerEventsListener(
    private val serverRepository: ServerRepository,
    private val sessionService: SessionService,
) : EventListener<ServerEvent> {

    override fun handle(event: ServerEvent) {
        when (event) {
            is ServerEvent.ServerCreatedEvent -> onServerCreated(event)
            is ServerEvent.ServerDeletedEvent -> onServerDeleted(event)
            is ServerEvent.ServerUserAddedEvent -> onUserJoinedServer(event)
            is ServerEvent.ServerUserRemovedEvent -> onUserLeftServer(event)
            is ServerEvent.ServerUserKickedEvent -> onUserKickedFromServer(event)
            is ServerEvent.ServerUpdatedEvent -> {}
        }
    }

    private fun onServerCreated(event: ServerEvent.ServerCreatedEvent) {
        val server = Server(id = ServerId(event.serverId), members = setOf(Username(event.owner)))
        serverRepository.save(server)
    }

    private fun onServerDeleted(event: ServerEvent.ServerDeletedEvent) {
        serverRepository.deleteById(ServerId(event.serverId))
    }

    private fun onUserJoinedServer(event: ServerEvent.ServerUserAddedEvent) {
        val server =
            serverRepository
                .findById(ServerId(event.serverId))
                .orThrow(ServerErrors.ServerNotFound(ServerId(event.serverId)))
        server.addMember(Username(event.username))
        serverRepository.save(server)
        server.channels().forEach {
            sessionService.addAllowedUser(AddAllowedUser(it.sessionId, Username(event.username)))
        }
    }

    private fun onUserLeftServer(event: ServerEvent.ServerUserRemovedEvent) {
        val server =
            serverRepository
                .findById(ServerId(event.serverId))
                .orThrow(ServerErrors.ServerNotFound(ServerId(event.serverId)))
        server.removeMember(Username(event.username))
        serverRepository.save(server)
        server.channels().forEach {
            sessionService.removeAllowedUser(
                RemoveAllowedUser(it.sessionId, Username(event.username))
            )
        }
    }

    private fun onUserKickedFromServer(event: ServerEvent.ServerUserKickedEvent) {
        onUserLeftServer(ServerEvent.ServerUserRemovedEvent(event.serverId, event.username))
    }
}
