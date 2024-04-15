package piperkt.services.multimedia.infrastructure.events.listeners

import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.Topic
import piperkt.services.multimedia.application.server.ChannelEventsListener
import piperkt.services.multimedia.domain.server.ChannelEvent

@KafkaListener
class ChannelEventsKafkaListener(private val channelEventsListener: ChannelEventsListener) {
    @Topic("channel-created")
    fun onChannelCreated(event: ChannelEvent.ChannelCreated) = channelEventsListener.handle(event)

    @Topic("channel-deleted")
    fun onChannelDeleted(event: ChannelEvent.ChannelDeleted) = channelEventsListener.handle(event)
}
