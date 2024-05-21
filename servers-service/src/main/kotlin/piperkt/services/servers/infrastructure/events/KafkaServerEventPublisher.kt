package piperkt.services.servers.infrastructure.events

import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.Topic
import jakarta.inject.Singleton
import piperkt.events.*

@KafkaClient
interface KafkaServerEventPublisher {

    @Topic("server-events") fun publish(event: ServerCreatedEvent)

    @Topic("server-events") fun publish(event: ServerDeletedEvent)

    @Topic("server-events") fun publish(event: ServerUpdatedEvent)

    @Topic("server-events") fun publish(event: ServerUserAddedEvent)

    @Topic("server-events") fun publish(event: ServerUserRemovedEvent)

    @Topic("server-events") fun publish(event: ServerUserKickedEvent)
}

@Singleton
class ServerEventPublisherImpl(private val kafkaServerEventPublisher: KafkaServerEventPublisher) :
    ServerEventPublisher {
    override fun publish(event: ServerEvent) {
        when (event) {
            is ServerCreatedEvent -> kafkaServerEventPublisher.publish(event)
            is ServerDeletedEvent -> kafkaServerEventPublisher.publish(event)
            is ServerUpdatedEvent -> kafkaServerEventPublisher.publish(event)
            is ServerUserAddedEvent -> kafkaServerEventPublisher.publish(event)
            is ServerUserRemovedEvent -> kafkaServerEventPublisher.publish(event)
            is ServerUserKickedEvent -> kafkaServerEventPublisher.publish(event)
        }
    }
}
