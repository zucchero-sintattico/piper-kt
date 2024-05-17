package piperkt.services.multimedia.application.server

import piperkt.common.events.ChannelEvent
import piperkt.common.events.EventListener
import piperkt.common.utils.orThrow
import piperkt.services.multimedia.application.session.SessionService
import piperkt.services.multimedia.application.session.SessionService.Command.CreateSession
import piperkt.services.multimedia.application.session.SessionService.Command.DeleteSession
import piperkt.services.multimedia.domain.server.Channel
import piperkt.services.multimedia.domain.server.ChannelId
import piperkt.services.multimedia.domain.server.ServerErrors
import piperkt.services.multimedia.domain.server.ServerId
import piperkt.services.multimedia.domain.server.ServerRepository

open class ChannelEventsListener(
    private val serverRepository: ServerRepository,
    private val sessionService: SessionService,
) : EventListener<ChannelEvent> {

    override fun handle(event: ChannelEvent) {
        when (event) {
            is ChannelEvent.ChannelCreatedEvent -> onChannelCreated(event)
            is ChannelEvent.ChannelDeletedEvent -> onChannelDeleted(event)
            is ChannelEvent.ChannelUpdatedEvent -> {}
            is ChannelEvent.MessageInChannelEvent -> {}
        }
    }

    private fun onChannelCreated(event: ChannelEvent.ChannelCreatedEvent) {
        val server =
            serverRepository
                .findById(ServerId(event.serverId))
                .orThrow(ServerErrors.ServerNotFound(ServerId(event.serverId)))
        val session = sessionService.createSession(CreateSession(server.members()))
        val channel = Channel(id = ChannelId(event.channelId), sessionId = session.id)
        server.addChannel(channel)
        serverRepository.save(server)
    }

    private fun onChannelDeleted(event: ChannelEvent.ChannelDeletedEvent) {
        val server =
            serverRepository
                .findById(ServerId(event.serverId))
                .orThrow(ServerErrors.ServerNotFound(ServerId(event.serverId)))
        val channel = server.getChannelById(ChannelId(event.channelId))
        sessionService.deleteSession(DeleteSession(channel.sessionId))
        server.removeChannelById(ChannelId(event.channelId))
        serverRepository.save(server)
    }
}
