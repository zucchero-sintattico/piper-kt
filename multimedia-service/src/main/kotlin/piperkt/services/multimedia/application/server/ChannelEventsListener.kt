package piperkt.services.multimedia.application.server

import piperkt.common.events.EventListener
import piperkt.common.utils.orThrow
import piperkt.events.ChannelCreatedEvent
import piperkt.events.ChannelDeletedEvent
import piperkt.events.ChannelEvent
import piperkt.events.ChannelUpdatedEvent
import piperkt.events.MessageInChannelEvent
import piperkt.services.multimedia.application.session.SessionService
import piperkt.services.multimedia.application.session.SessionService.Command.CreateSession
import piperkt.services.multimedia.application.session.SessionService.Command.DeleteSession
import piperkt.services.multimedia.domain.server.Channel
import piperkt.services.multimedia.domain.server.ChannelId
import piperkt.services.multimedia.domain.server.ServerErrors
import piperkt.services.multimedia.domain.server.ServerId
import piperkt.services.multimedia.domain.server.ServerRepository

/** Listens for events related to channels and updates the server repository accordingly. */
open class ChannelEventsListener(
    private val serverRepository: ServerRepository,
    private val sessionService: SessionService,
) : EventListener<ChannelEvent> {

    override fun handle(event: ChannelEvent) {
        when (event) {
            is ChannelCreatedEvent -> onChannelCreated(event)
            is ChannelDeletedEvent -> onChannelDeleted(event)
            is ChannelUpdatedEvent -> {}
            is MessageInChannelEvent -> {}
        }
    }

    private fun onChannelCreated(event: ChannelCreatedEvent) {
        val server =
            serverRepository
                .findById(ServerId(event.serverId))
                .orThrow(ServerErrors.ServerNotFound(ServerId(event.serverId)))
        val session = sessionService.createSession(CreateSession(server.members()))
        val channel = Channel(id = ChannelId(event.channelId), sessionId = session.id)
        server.addChannel(channel)
        serverRepository.update(server)
    }

    private fun onChannelDeleted(event: ChannelDeletedEvent) {
        val server =
            serverRepository
                .findById(ServerId(event.serverId))
                .orThrow(ServerErrors.ServerNotFound(ServerId(event.serverId)))
        val channel = server.getChannelById(ChannelId(event.channelId))
        sessionService.deleteSession(DeleteSession(channel.sessionId))
        server.removeChannelById(ChannelId(event.channelId))
        serverRepository.update(server)
    }
}
