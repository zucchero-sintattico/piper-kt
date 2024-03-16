package piperkt.services.multimedia.infrastructure.events.listeners

import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.Topic
import piperkt.services.multimedia.application.users.UserKickedFromServerEventListener
import piperkt.services.multimedia.application.users.events.UserKickedFromServer

@KafkaListener
class UserKickedFromServerKafkaListener(
    private val userKickedFromServerEventListener: UserKickedFromServerEventListener
) {
    @Topic("user-kicked-from-server")
    fun handle(userKickedFromServer: UserKickedFromServer) {
        userKickedFromServerEventListener.handle(userKickedFromServer)
    }
}
