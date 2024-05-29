package piperkt.services.multimedia.infrastructure.events.listeners

import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.Topic
import piperkt.events.ChannelCreatedEvent
import piperkt.events.ChannelDeletedEvent
import piperkt.services.multimedia.application.server.ChannelEventsListener

/**
 * Listens to channel events from Kafka.
 *
 * @param channelEventsListener The listener to delegate the events to.
 */
@KafkaListener
class ChannelEventsKafkaListener(private val channelEventsListener: ChannelEventsListener) {
    @Topic(ChannelCreatedEvent.TOPIC)
    fun onChannelCreated(event: ChannelCreatedEvent) = channelEventsListener.handle(event)

    @Topic(ChannelDeletedEvent.TOPIC)
    fun onChannelDeleted(event: ChannelDeletedEvent) = channelEventsListener.handle(event)
}
