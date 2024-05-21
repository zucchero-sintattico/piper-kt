package piperkt.services.servers.infrastructure.events

import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.Topic
import jakarta.inject.Singleton
import piperkt.events.ChannelCreatedEvent
import piperkt.events.ChannelDeletedEvent
import piperkt.events.ChannelEvent
import piperkt.events.ChannelEventPublisher
import piperkt.events.ChannelUpdatedEvent
import piperkt.events.MessageInChannelEvent

@KafkaClient
interface KafkaChannelEventPublisher {

    @Topic("channel-events") fun publish(event: ChannelCreatedEvent)

    @Topic("channel-events") fun publish(event: ChannelDeletedEvent)

    @Topic("channel-events") fun publish(event: ChannelUpdatedEvent)

    @Topic("channel-events") fun publish(event: MessageInChannelEvent)
}

@Singleton
class ChannelEventPublisherImpl(
    private val kafkaChannelEventPublisher: KafkaChannelEventPublisher,
) : ChannelEventPublisher {
    override fun publish(event: ChannelEvent) {
        when (event) {
            is ChannelCreatedEvent -> kafkaChannelEventPublisher.publish(event)
            is ChannelDeletedEvent -> kafkaChannelEventPublisher.publish(event)
            is ChannelUpdatedEvent -> kafkaChannelEventPublisher.publish(event)
            is MessageInChannelEvent -> kafkaChannelEventPublisher.publish(event)
        }
    }
}
