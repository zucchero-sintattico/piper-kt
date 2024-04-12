package piperkt.services.multimedia.application.listeners

import piperkt.common.EventListener
import piperkt.services.multimedia.domain.server.Channel
import piperkt.services.multimedia.domain.server.ChannelEvent
import piperkt.services.multimedia.domain.server.ChannelId
import piperkt.services.multimedia.domain.server.ServerId
import piperkt.services.multimedia.domain.server.ServerRepository
import piperkt.services.multimedia.domain.session.SessionFactory
import piperkt.services.multimedia.domain.session.SessionRepository

open class ChannelEventsListener(
    private val serverRepository: ServerRepository,
    private val sessionRepository: SessionRepository,
) : EventListener<ChannelEvent> {
    override fun handle(event: ChannelEvent) {
        when (event) {
            is ChannelEvent.ChannelCreated -> onChannelCreated(event)
            is ChannelEvent.ChannelDeleted -> onChannelDeleted(event)
        }
    }

    private fun onChannelCreated(event: ChannelEvent.ChannelCreated) {
        val server = serverRepository.findById(ServerId(event.sessionId))!!
        val session = SessionFactory.fromAllowedUsers(server.members().toSet())
        sessionRepository.save(session)
        val channel = Channel(id = ChannelId(event.channelId), sessionId = session.id)
        server.addChannel(channel)
        serverRepository.save(server)
    }

    private fun onChannelDeleted(event: ChannelEvent.ChannelDeleted) {
        val server = serverRepository.findById(ServerId(event.sessionId))!!
        val channel = server.getChannelById(ChannelId(event.channelId))
        sessionRepository.deleteById(channel.sessionId)
        server.removeChannel(channel)
        serverRepository.save(server)
    }
}
