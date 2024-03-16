package piperkt.services.multimedia.infrastructure.events.listeners

import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.Topic
import piperkt.services.multimedia.application.users.UserJoinedServerEventListener
import piperkt.services.multimedia.application.users.events.UserJoinedServer

@KafkaListener
class UserJoinedServerKafkaListener(
    private val userJoinedServerEventListener: UserJoinedServerEventListener
) {
    @Topic("user-joined-server")
    fun handle(userJoinedServer: UserJoinedServer) {
        userJoinedServerEventListener.handle(userJoinedServer)
    }
}
