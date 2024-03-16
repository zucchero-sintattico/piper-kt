package piperkt.services.multimedia.infrastructure.events.listeners

import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.Topic
import piperkt.services.multimedia.application.servers.events.ServerCreated
import piperkt.services.multimedia.application.servers.listeners.ServerCreatedEventListener

@KafkaListener
class ServerCreatedKafkaListener(
    private val serverCreatedEventListener: ServerCreatedEventListener
) {
    @Topic("server-created")
    fun handle(serverCreated: ServerCreated) {
        serverCreatedEventListener.handle(serverCreated)
    }
}
