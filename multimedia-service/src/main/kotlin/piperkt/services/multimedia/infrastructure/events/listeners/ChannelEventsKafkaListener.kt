package piperkt.services.multimedia.infrastructure.events.listeners

import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.Topic
import piperkt.events.ChannelCreatedEvent
import piperkt.events.ChannelDeletedEvent
import piperkt.services.multimedia.application.server.ChannelEventsListener

@KafkaListener
class ChannelEventsKafkaListener(private val channelEventsListener: ChannelEventsListener) {
    @Topic("channel-created")
    fun onChannelCreated(event: ChannelCreatedEvent) = channelEventsListener.handle(event)

    @Topic("channel-deleted")
    fun onChannelDeleted(event: ChannelDeletedEvent) = channelEventsListener.handle(event)
}
