package piperkt.services.multimedia.infrastructure.events.listeners

import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.Topic
import piperkt.events.ServerCreatedEvent
import piperkt.events.ServerDeletedEvent
import piperkt.events.ServerUserAddedEvent
import piperkt.events.ServerUserKickedEvent
import piperkt.events.ServerUserRemovedEvent
import piperkt.services.multimedia.application.server.ServerEventsListener

/**
 * Listens to server events from Kafka.
 *
 * @param serverEventsListener The listener to delegate the events to.
 */
@KafkaListener
class ServerEventsKafkaListener(private val serverEventsListener: ServerEventsListener) {
    @Topic(ServerCreatedEvent.TOPIC)
    fun onServerCreated(event: ServerCreatedEvent) = serverEventsListener.handle(event)

    @Topic(ServerDeletedEvent.TOPIC)
    fun onServerDeleted(event: ServerDeletedEvent) = serverEventsListener.handle(event)

    @Topic(ServerUserAddedEvent.TOPIC)
    fun onUserJoinedServer(event: ServerUserAddedEvent) = serverEventsListener.handle(event)

    @Topic(ServerUserRemovedEvent.TOPIC)
    fun onUserLeftServer(event: ServerUserRemovedEvent) = serverEventsListener.handle(event)

    @Topic(ServerUserKickedEvent.TOPIC)
    fun onUserKickedFromServer(event: ServerUserKickedEvent) = serverEventsListener.handle(event)
}
