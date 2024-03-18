package piperkt.services.multimedia.infrastructure.events.listeners

import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.Topic
import piperkt.services.multimedia.application.users.listeners.UserLeftServerEventListener
import piperkt.services.multimedia.domain.users.events.UserLeftServer

@KafkaListener
class UserLeftServerKafkaListener(
    private val userLeftServerEventListener: UserLeftServerEventListener
) {
    @Topic("user-left-server")
    fun handle(userLeftServer: UserLeftServer) {
        userLeftServerEventListener.handle(userLeftServer)
    }
}
