package piperkt.services.friendships.infrastructure.events

import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.Topic
import jakarta.inject.Singleton
import piperkt.events.*

@KafkaClient
interface KafkaFriendshipEventPublisher {

    @Topic("friendship-events") fun publish(event: FriendshipRequestSentEvent)

    @Topic("friendship-events") fun publish(event: FriendshipRequestAcceptedEvent)

    @Topic("friendship-events") fun publish(event: NewMessageInFriendshipEvent)

    @Topic("friendship-events") fun publish(event: FriendshipRequestRejectedEvent)
}

@Singleton
class FriendshipEventPublisherImpl(
    private val kafkaFriendshipEventPublisher: KafkaFriendshipEventPublisher,
) : FriendshipEventPublisher {
    override fun publish(event: FriendshipEvent) {
        when (event) {
            is FriendshipRequestAcceptedEvent -> kafkaFriendshipEventPublisher.publish(event)
            is FriendshipRequestSentEvent -> kafkaFriendshipEventPublisher.publish(event)
            is NewMessageInFriendshipEvent -> kafkaFriendshipEventPublisher.publish(event)
            is FriendshipRequestRejectedEvent -> kafkaFriendshipEventPublisher.publish(event)
        }
    }
}
