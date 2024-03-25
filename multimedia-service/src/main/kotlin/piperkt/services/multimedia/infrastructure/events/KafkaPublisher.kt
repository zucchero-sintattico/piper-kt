package piperkt.services.multimedia.infrastructure.events

import jakarta.inject.Singleton
import piperkt.services.multimedia.application.DomainEvent
import piperkt.services.multimedia.application.EventPublisher

@Singleton
class KafkaPublisher : EventPublisher {
    override fun publish(event: DomainEvent) {
        println("Publishing event: $event")
    }
}
