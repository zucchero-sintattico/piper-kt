package piperkt.services.multimedia.infrastructure.implementation

import jakarta.inject.Singleton
import piperkt.services.multimedia.application.servers.listeners.ServerCreatedEventListener
import piperkt.services.multimedia.application.servers.listeners.ServerDeletedEventListener
import piperkt.services.multimedia.application.users.listeners.UserJoinedServerEventListener
import piperkt.services.multimedia.application.users.listeners.UserKickedFromServerEventListener
import piperkt.services.multimedia.application.users.listeners.UserLeftServerEventListener
import piperkt.services.multimedia.domain.servers.ServerRepository

object EventListeners {

    @Singleton
    class UserJoinedServerEventListenerService(serverRepository: ServerRepository) :
        UserJoinedServerEventListener(serverRepository)

    @Singleton
    class UserKickedFromServerEventListenerService(serverRepository: ServerRepository) :
        UserKickedFromServerEventListener(serverRepository)

    @Singleton
    class UserLeftServerEventListenerService(serverRepository: ServerRepository) :
        UserLeftServerEventListener(serverRepository)

    @Singleton
    class ServerCreatedEventListenerService(serverRepository: ServerRepository) :
        ServerCreatedEventListener(serverRepository)

    @Singleton
    class ServerDeletedEventListenerService(serverRepository: ServerRepository) :
        ServerDeletedEventListener(serverRepository)
}
