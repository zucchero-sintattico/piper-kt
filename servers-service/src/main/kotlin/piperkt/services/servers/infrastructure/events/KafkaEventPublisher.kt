package piperkt.services.servers.infrastructure.events

import jakarta.inject.Singleton
import piperkt.services.commons.domain.events.DomainEvent
import piperkt.services.commons.domain.events.DomainEventPublisher

@Singleton
class KafkaEventPublisher : DomainEventPublisher {
    override fun publish(event: DomainEvent) {
        println("Publishing event: $event")
    }
}

/**
 * @Singleton class KafkaEventPublisher(): EventPublisher {
 *
 * override fun publish(any: Any) {
 *
 * }
 *
 * }
 *
 * @KafkaListener class KafkaListener(userEventsService: UserEventsService): UserEventsApi {
 * @Topic("user-created") override fun onUserCreated(userCreated: UserCreated) {
 *   userEventsService.onUserCreated(userCreated) }
 *
 * }
 */
