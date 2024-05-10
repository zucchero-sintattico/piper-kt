package piperkt.services.users.infrastructure.events.publishers

import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.Topic
import io.micronaut.serde.annotation.SerdeImport
import jakarta.inject.Singleton
import piperkt.services.users.domain.user.UserEvent
import piperkt.services.users.domain.user.UserEventPublisher

@SerdeImport(UserEvent.UserCreated::class)
@SerdeImport(UserEvent.UserUpdated::class)
@KafkaClient
abstract class UserEventKafkaPublisher {

    @Topic("user-created") abstract fun onUserCreated(event: UserEvent.UserCreated)

    @Topic("user-updated") abstract fun onUserUpdated(event: UserEvent.UserUpdated)
}

@Singleton
class UserEventPublisherImpl(private val userEventKafkaPublisher: UserEventKafkaPublisher) :
    UserEventPublisher {
    override fun publish(event: UserEvent) {
        when (event) {
            is UserEvent.UserCreated -> userEventKafkaPublisher.onUserCreated(event)
            is UserEvent.UserUpdated -> userEventKafkaPublisher.onUserUpdated(event)
        }
    }
}
