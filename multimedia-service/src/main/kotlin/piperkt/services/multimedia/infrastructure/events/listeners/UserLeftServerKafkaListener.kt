package piperkt.services.multimedia.infrastructure.events.listeners

import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.Topic
import piperkt.services.multimedia.application.users.events.UserLeftServer
import piperkt.services.multimedia.application.users.listeners.UserLeftServerEventListener

@KafkaListener
class UserLeftServerKafkaListener(
    private val userLeftServerEventListener: UserLeftServerEventListener
) {
    @Topic("user-left-server")
    fun handle(userLeftServer: UserLeftServer) {
        userLeftServerEventListener.handle(userLeftServer)
    }
}
