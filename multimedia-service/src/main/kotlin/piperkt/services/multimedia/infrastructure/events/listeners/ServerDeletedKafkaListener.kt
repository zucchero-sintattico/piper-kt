package piperkt.services.multimedia.infrastructure.events.listeners

import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.Topic
import piperkt.services.multimedia.application.servers.listeners.ServerDeletedEventListener
import piperkt.services.multimedia.domain.servers.events.ServerDeleted

@KafkaListener
class ServerDeletedKafkaListener(
    private val serverDeletedEventListener: ServerDeletedEventListener
) {
    @Topic("server-deleted")
    fun handle(serverDeleted: ServerDeleted) {
        serverDeletedEventListener.handle(serverDeleted)
    }
}
