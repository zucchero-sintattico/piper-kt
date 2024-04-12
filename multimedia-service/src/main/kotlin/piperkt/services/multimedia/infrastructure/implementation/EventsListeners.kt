package piperkt.services.multimedia.infrastructure.implementation

import jakarta.inject.Singleton
import piperkt.services.multimedia.application.listeners.ChannelEventsListener
import piperkt.services.multimedia.application.listeners.DirectEventsListener
import piperkt.services.multimedia.application.listeners.ServerEventsListener
import piperkt.services.multimedia.application.services.DirectService
import piperkt.services.multimedia.application.services.ServerService
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
