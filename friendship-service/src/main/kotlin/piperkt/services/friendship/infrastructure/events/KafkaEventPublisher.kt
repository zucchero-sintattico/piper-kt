package piperkt.services.friendship.infrastructure.events

import jakarta.inject.Singleton
import piperkt.services.friendship.commons.events.Event
import piperkt.services.friendship.commons.events.EventPublisher

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
