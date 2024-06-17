package piperkt.services.multimedia.infrastructure.events.listeners

import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.Topic
import piperkt.events.FriendshipRequestAcceptedEvent
import piperkt.services.multimedia.application.direct.DirectEventsListener

/**
 * Listens to directs events from Kafka.
 *
 * @param directEventsListener The listener to delegate the events to.
 */
@KafkaListener
class DirectEventsKafkaListener(private val directEventsListener: DirectEventsListener) {
    @Topic(FriendshipRequestAcceptedEvent.TOPIC)
    fun onFriendRequestAccepted(event: FriendshipRequestAcceptedEvent) =
        directEventsListener.handle(event)
}
