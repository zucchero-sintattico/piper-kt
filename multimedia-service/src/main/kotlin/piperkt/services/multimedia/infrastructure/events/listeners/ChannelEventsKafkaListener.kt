package piperkt.services.multimedia.infrastructure.events.listeners

import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.Topic
import piperkt.events.ChannelEvent
import piperkt.services.multimedia.application.server.ChannelEventsListener

@KafkaListener
class ChannelEventsKafkaListener(private val channelEventsListener: ChannelEventsListener) {
    @Topic("channel-created")
    fun onChannelCreated(event: ChannelEvent.ChannelCreatedEvent) =
        channelEventsListener.handle(event)

    @Topic("channel-deleted")
    fun onChannelDeleted(event: ChannelEvent.ChannelDeletedEvent) =
        channelEventsListener.handle(event)
}
