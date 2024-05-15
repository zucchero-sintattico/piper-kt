package piperkt.services.servers.infrastructure.events

import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.Topic
import jakarta.inject.Singleton
import piperkt.common.events.ChannelEvent
import piperkt.common.events.ChannelEventPublisher

@KafkaClient
interface KafkaChannelEventPublisher {

    @Topic("channel-events") fun publish(event: ChannelEvent.ChannelCreatedEvent)

    @Topic("channel-events") fun publish(event: ChannelEvent.ChannelDeletedEvent)

    @Topic("channel-events") fun publish(event: ChannelEvent.ChannelUpdatedEvent)

    @Topic("channel-events") fun publish(event: ChannelEvent.MessageInChannelEvent)
}

@Singleton
class ChannelEventPublisherImpl(
    private val kafkaChannelEventPublisher: KafkaChannelEventPublisher
) : ChannelEventPublisher {
    override fun publish(event: ChannelEvent) {
        when (event) {
            is ChannelEvent.ChannelCreatedEvent -> kafkaChannelEventPublisher.publish(event)
            is ChannelEvent.ChannelDeletedEvent -> kafkaChannelEventPublisher.publish(event)
            is ChannelEvent.ChannelUpdatedEvent -> kafkaChannelEventPublisher.publish(event)
            is ChannelEvent.MessageInChannelEvent -> kafkaChannelEventPublisher.publish(event)
        }
    }
}
