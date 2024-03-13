package piperkt.services.multimedia.infrastructure.events.listeners

import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.Topic
import piperkt.services.multimedia.application.servers.ServersEventsListener
import piperkt.services.multimedia.application.servers.events.ServerCreated
import piperkt.services.multimedia.application.servers.events.ServerDeleted

@KafkaListener
class KafkaServersEventListener(private val serversEventsListener: ServersEventsListener) {

    @Topic("server-created")
    fun onServerCreated(serverCreated: ServerCreated) {
        serversEventsListener.onServerCreated(serverCreated)
    }

    @Topic("server-deleted")
    fun onServerDeleted(serverDeleted: ServerDeleted) {
        serversEventsListener.onServerDeleted(serverDeleted)
    }
}
