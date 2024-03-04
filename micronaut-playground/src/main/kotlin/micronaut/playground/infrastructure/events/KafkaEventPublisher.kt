package micronaut.playground.infrastructure.events

import jakarta.inject.Singleton
import micronaut.playground.commons.events.Event
import micronaut.playground.commons.events.EventPublisher

@Singleton
class KafkaEventPublisher : EventPublisher {
    override fun publish(event: Event) {
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
