package piperkt.services.multimedia.infrastructure.implementation

import jakarta.inject.Singleton
import piperkt.services.multimedia.application.direct.DirectEventsListener
import piperkt.services.multimedia.application.server.ChannelEventsListener
import piperkt.services.multimedia.application.server.ServerEventsListener
import piperkt.services.multimedia.application.session.SessionService
import piperkt.services.multimedia.domain.direct.DirectRepository
import piperkt.services.multimedia.domain.server.ServerRepository

object EventsListeners {
    @Singleton
    class ServerEventListenerService(
        serverRepository: ServerRepository,
        sessionService: SessionService
    ) : ServerEventsListener(serverRepository, sessionService)

    @Singleton
    class DirectEventListenerService(
        directRepository: DirectRepository,
        sessionService: SessionService
    ) : DirectEventsListener(directRepository, sessionService)

    @Singleton
    class ChannelEventListenerService(
        serverRepository: ServerRepository,
        sessionService: SessionService
    ) : ChannelEventsListener(serverRepository, sessionService)
}
