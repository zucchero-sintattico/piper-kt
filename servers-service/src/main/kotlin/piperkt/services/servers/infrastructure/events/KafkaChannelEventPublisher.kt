package piperkt.services.servers.infrastructure.events

import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.Topic
import piperkt.common.events.ChannelEvent
import piperkt.common.events.ChannelEventPublisher

@KafkaClient
interface KafkaChannelEventPublisher : ChannelEventPublisher {
    override fun publish(event: ChannelEvent) {
        when (event) {
            is ChannelEvent.ChannelCreatedEvent -> publish(event)
            is ChannelEvent.ChannelDeletedEvent -> publish(event)
            is ChannelEvent.ChannelUpdatedEvent -> publish(event)
            is ChannelEvent.MessageInChannelEvent -> publish(event)
        }
    }

    @Topic("channel-events") fun publish(event: ChannelEvent.ChannelCreatedEvent)

    @Topic("channel-events") fun publish(event: ChannelEvent.ChannelDeletedEvent)

    @Topic("channel-events") fun publish(event: ChannelEvent.ChannelUpdatedEvent)

    @Topic("channel-events") fun publish(event: ChannelEvent.MessageInChannelEvent)
}
