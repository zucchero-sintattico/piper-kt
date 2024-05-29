package piperkt.services.multimedia.application.server

import piperkt.common.events.EventListener
import piperkt.common.utils.orThrow
import piperkt.events.ServerCreatedEvent
import piperkt.events.ServerDeletedEvent
import piperkt.events.ServerEvent
import piperkt.events.ServerUpdatedEvent
import piperkt.events.ServerUserAddedEvent
import piperkt.events.ServerUserKickedEvent
import piperkt.events.ServerUserRemovedEvent
import piperkt.services.multimedia.application.session.SessionService
import piperkt.services.multimedia.application.session.SessionService.Command.AddAllowedUser
import piperkt.services.multimedia.application.session.SessionService.Command.RemoveAllowedUser
import piperkt.services.multimedia.domain.server.Server
import piperkt.services.multimedia.domain.server.ServerErrors
import piperkt.services.multimedia.domain.server.ServerId
import piperkt.services.multimedia.domain.server.ServerRepository
import piperkt.services.multimedia.domain.user.Username

/** Listens for events related to servers and updates the server repository accordingly. */
open class ServerEventsListener(
    private val serverRepository: ServerRepository,
    private val sessionService: SessionService,
) : EventListener<ServerEvent> {

    override fun handle(event: ServerEvent) {
        when (event) {
            is ServerCreatedEvent -> onServerCreated(event)
            is ServerDeletedEvent -> onServerDeleted(event)
            is ServerUserAddedEvent -> onUserJoinedServer(event)
            is ServerUserRemovedEvent -> onUserLeftServer(event)
            is ServerUserKickedEvent -> onUserKickedFromServer(event)
            is ServerUpdatedEvent -> {}
        }
    }

    private fun onServerCreated(event: ServerCreatedEvent) {
        val server = Server(id = ServerId(event.serverId), members = setOf(Username(event.owner)))
        serverRepository.save(server)
    }

    private fun onServerDeleted(event: ServerDeletedEvent) {
        serverRepository.deleteById(ServerId(event.serverId))
    }

    private fun onUserJoinedServer(event: ServerUserAddedEvent) {
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

    private fun onUserLeftServer(event: ServerUserRemovedEvent) {
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

    private fun onUserKickedFromServer(event: ServerUserKickedEvent) {
        onUserLeftServer(
            ServerUserRemovedEvent(serverId = event.serverId, username = event.username)
        )
    }
}
