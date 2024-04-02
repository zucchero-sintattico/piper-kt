package piperkt.services.multimedia.infrastructure.implementation

import jakarta.inject.Singleton
import piperkt.services.multimedia.application.listeners.ServerEventsListener
import piperkt.services.multimedia.application.listeners.UserEventsListener
import piperkt.services.multimedia.domain.server.ServerRepository

object EventsListeners {
    @Singleton
    class ServerEventListenerService(serverRepository: ServerRepository) :
        ServerEventsListener(serverRepository)

    @Singleton
    class UserEventListenerService(serverRepository: ServerRepository) :
        UserEventsListener(serverRepository)
}
