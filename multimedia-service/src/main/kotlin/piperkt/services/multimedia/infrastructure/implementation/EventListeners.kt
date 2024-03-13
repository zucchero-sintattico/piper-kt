package piperkt.services.multimedia.infrastructure.implementation

import jakarta.inject.Singleton
import piperkt.services.multimedia.application.servers.ServersEventsListener
import piperkt.services.multimedia.application.users.UsersEventsListener
import piperkt.services.multimedia.domain.servers.ServerRepository

object EventListeners {
    @Singleton
    class ServersEventListenerImpl(serverRepository: ServerRepository) :
        ServersEventsListener(serverRepository)

    @Singleton
    class UsersEventsListenerImpl(serverRepository: ServerRepository) :
        UsersEventsListener(serverRepository)
}
