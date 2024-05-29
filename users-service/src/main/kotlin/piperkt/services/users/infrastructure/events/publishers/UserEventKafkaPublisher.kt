package piperkt.services.users.infrastructure.events.publishers

import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.Topic
import io.micronaut.serde.annotation.SerdeImport
import jakarta.inject.Singleton
import piperkt.events.UserCreatedEvent
import piperkt.events.UserEvent
import piperkt.events.UserEventPublisher
import piperkt.events.UserLoggedInEvent
import piperkt.events.UserLoggedOutEvent
import piperkt.events.UserOfflineEvent
import piperkt.events.UserOnlineEvent
import piperkt.events.UserUpdatedEvent

@SerdeImport(UserCreatedEvent::class)
@SerdeImport(UserUpdatedEvent::class)
@SerdeImport(UserLoggedInEvent::class)
@SerdeImport(UserLoggedOutEvent::class)
@KafkaClient
abstract class UserEventKafkaPublisher {

    @Topic(UserCreatedEvent.TOPIC) abstract fun onUserCreated(event: UserCreatedEvent)

    @Topic(UserUpdatedEvent.TOPIC) abstract fun onUserUpdatedEvent(event: UserUpdatedEvent)

    @Topic(UserLoggedInEvent.TOPIC) abstract fun onUserLoggedInEvent(event: UserLoggedInEvent)

    @Topic(UserLoggedOutEvent.TOPIC) abstract fun onUserLoggedOutEvent(event: UserLoggedOutEvent)
}

@Singleton
class UserEventPublisherImpl(private val userEventKafkaPublisher: UserEventKafkaPublisher) :
    UserEventPublisher {
    override fun publish(event: UserEvent) {
        when (event) {
            is UserCreatedEvent -> userEventKafkaPublisher.onUserCreated(event)
            is UserUpdatedEvent -> userEventKafkaPublisher.onUserUpdatedEvent(event)
            is UserLoggedInEvent -> userEventKafkaPublisher.onUserLoggedInEvent(event)
            is UserLoggedOutEvent -> userEventKafkaPublisher.onUserLoggedOutEvent(event)
            is UserOfflineEvent -> {} // Nothing to do
            is UserOnlineEvent -> {} // Nothing to do
        }
    }
}
