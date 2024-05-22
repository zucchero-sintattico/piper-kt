package piperkt.services.users.infrastructure.events.publishers

import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.Topic
import io.micronaut.serde.annotation.SerdeImport
import jakarta.inject.Singleton
import piperkt.events.*

@SerdeImport(UserCreatedEvent::class)
@SerdeImport(UserUpdatedEvent::class)
@KafkaClient
abstract class UserEventKafkaPublisher {

    @Topic("user-created") abstract fun onUserCreated(event: UserCreatedEvent)

    @Topic("user-updated") abstract fun onUserUpdatedEvent(event: UserUpdatedEvent)
}

@Singleton
class UserEventPublisherImpl(private val userEventKafkaPublisher: UserEventKafkaPublisher) :
    UserEventPublisher {
    override fun publish(event: UserEvent) {
        when (event) {
            is UserCreatedEvent -> userEventKafkaPublisher.onUserCreated(event)
            is UserUpdatedEvent -> userEventKafkaPublisher.onUserUpdatedEvent(event)
            is UserLoggedInEvent -> {} // Nothing to do
            is UserLoggedOutEvent -> {} // Nothing to do
            is UserOfflineEvent -> {} // Nothing to do
            is UserOnlineEvent -> {} // Nothing to do
        }
    }
}
