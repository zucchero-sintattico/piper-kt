package piperkt.services.servers.infrastructure.events

import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.Topic
import piperkt.common.events.ServerEvent
import piperkt.common.events.ServerEventPublisher

@KafkaClient
interface KafkaServerEventPublisher : ServerEventPublisher {
    override fun publish(event: ServerEvent) {
        when (event) {
            is ServerEvent.ServerCreatedEvent -> publish(event)
            is ServerEvent.ServerDeletedEvent -> publish(event)
            is ServerEvent.ServerUpdatedEvent -> publish(event)
            is ServerEvent.ServerUserAddedEvent -> publish(event)
            is ServerEvent.ServerUserRemovedEvent -> publish(event)
            is ServerEvent.ServerUserKickedEvent -> publish(event)
        }
    }

    @Topic("server-events") fun publish(event: ServerEvent.ServerCreatedEvent)

    @Topic("server-events") fun publish(event: ServerEvent.ServerDeletedEvent)

    @Topic("server-events") fun publish(event: ServerEvent.ServerUpdatedEvent)

    @Topic("server-events") fun publish(event: ServerEvent.ServerUserAddedEvent)

    @Topic("server-events") fun publish(event: ServerEvent.ServerUserRemovedEvent)

    @Topic("server-events") fun publish(event: ServerEvent.ServerUserKickedEvent)
}
