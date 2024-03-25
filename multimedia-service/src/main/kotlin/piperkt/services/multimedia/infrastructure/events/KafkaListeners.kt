package piperkt.services.multimedia.infrastructure.events

import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.Topic
import piperkt.services.multimedia.application.listeners.ServerCreatedEventListener
import piperkt.services.multimedia.application.listeners.ServerDeletedEventListener
import piperkt.services.multimedia.application.listeners.UserJoinedServerEventListener
import piperkt.services.multimedia.application.listeners.UserKickedFromServerEventListener
import piperkt.services.multimedia.application.listeners.UserLeftServerEventListener
import piperkt.services.multimedia.domain.ServerRepository
import piperkt.services.multimedia.domain.events.ServerCreated
import piperkt.services.multimedia.domain.events.ServerDeleted
import piperkt.services.multimedia.domain.events.UserJoinedServer
import piperkt.services.multimedia.domain.events.UserKickedFromServer
import piperkt.services.multimedia.domain.events.UserLeftServer

object KafkaListeners {
    @KafkaListener
    class ServerCreatedKafkaListener(serverRepository: ServerRepository) :
        ServerCreatedEventListener(serverRepository) {
        @Topic("server-created")
        override fun handle(event: ServerCreated) {
            super.handle(event)
        }
    }

    @KafkaListener
    class ServerDeletedKafkaListener(serverRepository: ServerRepository) :
        ServerDeletedEventListener(serverRepository) {
        @Topic("server-deleted")
        override fun handle(event: ServerDeleted) {
            super.handle(event)
        }
    }

    @KafkaListener
    class UserJoinedServerKafkaListener(serverRepository: ServerRepository) :
        UserJoinedServerEventListener(serverRepository) {
        @Topic("user-joined-server")
        override fun handle(event: UserJoinedServer) {
            super.handle(event)
        }
    }

    @KafkaListener
    class UserLeftServerKafkaListener(serverRepository: ServerRepository) :
        UserLeftServerEventListener(serverRepository) {
        @Topic("user-left-server")
        override fun handle(event: UserLeftServer) {
            super.handle(event)
        }
    }

    @KafkaListener
    class UserKickedFromServerKafkaListener(serverRepository: ServerRepository) :
        UserKickedFromServerEventListener(serverRepository) {
        @Topic("user-kicked-from-server")
        override fun handle(event: UserKickedFromServer) {
            super.handle(event)
        }
    }
}
