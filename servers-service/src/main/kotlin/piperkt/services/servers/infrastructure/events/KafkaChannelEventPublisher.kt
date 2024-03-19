package piperkt.services.servers.infrastructure.events

import jakarta.inject.Singleton
import piperkt.services.commons.domain.events.ChannelEvent
import piperkt.services.commons.domain.events.ChannelEventPublisher

@Singleton
class KafkaChannelEventPublisher : ChannelEventPublisher {
    override fun publish(event: ChannelEvent) {
        when (event) {
            is ChannelEvent.ChannelCreatedEvent -> println("todo")
            is ChannelEvent.ChannelDeletedEvent -> println("todo")
            is ChannelEvent.ChannelUpdatedEvent -> println("todo")
            is ChannelEvent.MessageInChannelEvent -> println("todo")
        }
    }
}
