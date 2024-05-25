package piperkt.services.servers.infrastructure.events

import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.Topic
import jakarta.inject.Singleton
import piperkt.events.*

@KafkaClient
interface KafkaServerEventPublisher {

    @Topic(ServerCreatedEvent.TOPIC) fun publish(event: ServerCreatedEvent)

    @Topic(ServerDeletedEvent.TOPIC) fun publish(event: ServerDeletedEvent)

    @Topic(ServerUpdatedEvent.TOPIC) fun publish(event: ServerUpdatedEvent)

    @Topic(ServerUserAddedEvent.TOPIC) fun publish(event: ServerUserAddedEvent)

    @Topic(ServerUserRemovedEvent.TOPIC) fun publish(event: ServerUserRemovedEvent)

    @Topic(ServerUserKickedEvent.TOPIC) fun publish(event: ServerUserKickedEvent)
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
