package piperkt.services.servers.infrastructure.events

import jakarta.inject.Singleton
import piperkt.services.commons.domain.events.ServerEvent
import piperkt.services.commons.domain.events.ServerEventPublisher

@Singleton
class KafkaServerEventPublisher : ServerEventPublisher {
    override fun publish(event: ServerEvent) {
        when (event) {
            is ServerEvent.ServerCreatedEvent -> println("todo")
            is ServerEvent.ServerDeletedEvent -> println("todo")
            is ServerEvent.ServerUpdatedEvent -> println("todo")
            is ServerEvent.ServerUserAddedEvent -> println("todo")
            is ServerEvent.ServerUserRemovedEvent -> println("todo")
        }
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
