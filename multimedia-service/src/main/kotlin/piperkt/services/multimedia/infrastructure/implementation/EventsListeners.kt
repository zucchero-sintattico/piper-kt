package piperkt.services.multimedia.infrastructure.implementation

import jakarta.inject.Singleton
import piperkt.services.multimedia.application.listeners.ChannelEventsListener
import piperkt.services.multimedia.application.listeners.DirectEventsListener
import piperkt.services.multimedia.application.listeners.ServerEventsListener
import piperkt.services.multimedia.domain.direct.DirectRepository
import piperkt.services.multimedia.domain.server.ServerRepository
import piperkt.services.multimedia.domain.session.SessionEventPublisher
import piperkt.services.multimedia.domain.session.SessionRepository

object EventsListeners {
    @Singleton
    class ServerEventListenerService(serverRepository: ServerRepository) :
        ServerEventsListener(serverRepository)

    @Singleton
    class DirectEventListenerService(
        directRepository: DirectRepository,
        sessionRepository: SessionRepository,
        sessionEventPublisher: SessionEventPublisher
    ) : DirectEventsListener(directRepository, sessionRepository, sessionEventPublisher)

    @Singleton
    class ChannelEventListenerService(
        serverRepository: ServerRepository,
        sessionRepository: SessionRepository
    ) : ChannelEventsListener(serverRepository, sessionRepository)
}
