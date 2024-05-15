package piperkt.services.servers.infrastructure.events

import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.Topic
import jakarta.inject.Singleton
import piperkt.common.events.ServerEvent
import piperkt.common.events.ServerEventPublisher

@KafkaClient
interface KafkaServerEventPublisher {

    @Topic("server-events") fun publish(event: ServerEvent.ServerCreatedEvent)

    @Topic("server-events") fun publish(event: ServerEvent.ServerDeletedEvent)

    @Topic("server-events") fun publish(event: ServerEvent.ServerUpdatedEvent)

    @Topic("server-events") fun publish(event: ServerEvent.ServerUserAddedEvent)

    @Topic("server-events") fun publish(event: ServerEvent.ServerUserRemovedEvent)

    @Topic("server-events") fun publish(event: ServerEvent.ServerUserKickedEvent)
}

@Singleton
class ServerEventPublisherImpl(private val kafkaServerEventPublisher: KafkaServerEventPublisher) :
    ServerEventPublisher {
    override fun publish(event: ServerEvent) {
        when (event) {
            is ServerEvent.ServerCreatedEvent -> kafkaServerEventPublisher.publish(event)
            is ServerEvent.ServerDeletedEvent -> kafkaServerEventPublisher.publish(event)
            is ServerEvent.ServerUpdatedEvent -> kafkaServerEventPublisher.publish(event)
            is ServerEvent.ServerUserAddedEvent -> kafkaServerEventPublisher.publish(event)
            is ServerEvent.ServerUserRemovedEvent -> kafkaServerEventPublisher.publish(event)
            is ServerEvent.ServerUserKickedEvent -> kafkaServerEventPublisher.publish(event)
        }
    }
}
