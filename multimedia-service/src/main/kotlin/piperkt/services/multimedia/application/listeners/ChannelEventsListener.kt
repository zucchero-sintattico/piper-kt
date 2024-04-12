package piperkt.services.multimedia.application.listeners

import piperkt.common.EventListener
import piperkt.services.multimedia.application.services.ServerService
import piperkt.services.multimedia.application.services.ServerService.Command.AddServerChannel
import piperkt.services.multimedia.application.services.ServerService.Command.RemoveServerChannel
import piperkt.services.multimedia.application.services.SessionService
import piperkt.services.multimedia.application.services.SessionService.Command.CreateSession
import piperkt.services.multimedia.application.services.SessionService.Command.DeleteSession
import piperkt.services.multimedia.domain.server.Channel
import piperkt.services.multimedia.domain.server.ChannelEvent
import piperkt.services.multimedia.domain.server.ChannelId
import piperkt.services.multimedia.domain.server.ServerId

open class ChannelEventsListener(
    private val serverService: ServerService,
    private val sessionService: SessionService
) : EventListener<ChannelEvent> {

    override fun handle(event: ChannelEvent) {
        when (event) {
            is ChannelEvent.ChannelCreated -> onChannelCreated(event)
            is ChannelEvent.ChannelDeleted -> onChannelDeleted(event)
        }
    }

    private fun onChannelCreated(event: ChannelEvent.ChannelCreated) {
        val server = serverService.getServer(ServerId(event.sessionId))
        val session = sessionService.createSession(CreateSession(server.members()))
        val channel = Channel(id = ChannelId(event.channelId), sessionId = session.id)
        serverService.addServerChannel(AddServerChannel(serverId = server.id, channel = channel))
    }

    private fun onChannelDeleted(event: ChannelEvent.ChannelDeleted) {
        val server = serverService.getServer(ServerId(event.sessionId))
        val channel = server.getChannelById(ChannelId(event.channelId))
        sessionService.deleteSession(DeleteSession(channel.sessionId))
        serverService.removeServerChannel(
            RemoveServerChannel(serverId = server.id, channelId = channel.id)
        )
    }
}
