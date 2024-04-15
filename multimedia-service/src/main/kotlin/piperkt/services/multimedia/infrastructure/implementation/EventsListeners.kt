package piperkt.services.multimedia.infrastructure.implementation

import jakarta.inject.Singleton
import piperkt.services.multimedia.application.direct.DirectEventsListener
import piperkt.services.multimedia.application.direct.DirectService
import piperkt.services.multimedia.application.server.ChannelEventsListener
import piperkt.services.multimedia.application.server.ServerEventsListener
import piperkt.services.multimedia.application.server.ServerService
import piperkt.services.multimedia.application.services.SessionService

object EventsListeners {
    @Singleton
    class ServerEventListenerService(serverService: ServerService) :
        ServerEventsListener(serverService)

    @Singleton
    class DirectEventListenerService(directService: DirectService, sessionService: SessionService) :
        DirectEventsListener(directService, sessionService)

    @Singleton
    class ChannelEventListenerService(
        serverService: ServerService,
        sessionService: SessionService
    ) : ChannelEventsListener(serverService, sessionService)
}
