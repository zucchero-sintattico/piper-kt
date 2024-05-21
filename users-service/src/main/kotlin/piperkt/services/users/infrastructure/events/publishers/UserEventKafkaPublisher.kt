package piperkt.services.users.infrastructure.events.publishers

import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.Topic
import io.micronaut.serde.annotation.SerdeImport
import jakarta.inject.Singleton
import piperkt.events.UserCreated
import piperkt.events.UserEvent
import piperkt.events.UserEventPublisher
import piperkt.events.UserUpdated

@SerdeImport(UserCreated::class)
@SerdeImport(UserUpdated::class)
@KafkaClient
abstract class UserEventKafkaPublisher {

    @Topic("user-created") abstract fun onUserCreated(event: UserCreated)

    @Topic("user-updated") abstract fun onUserUpdated(event: UserUpdated)
}

@Singleton
class UserEventPublisherImpl(private val userEventKafkaPublisher: UserEventKafkaPublisher) :
    UserEventPublisher {
    override fun publish(event: UserEvent) {
        when (event) {
            is UserCreated -> userEventKafkaPublisher.onUserCreated(event)
            is UserUpdated -> userEventKafkaPublisher.onUserUpdated(event)
        }
    }
}
