package piperkt.services.friendships.infrastructure.events

import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.Topic
import jakarta.inject.Singleton
import piperkt.events.*

@KafkaClient
interface KafkaFriendshipEventPublisher {

    @Topic(FriendshipRequestSentEvent.TOPIC) fun publish(event: FriendshipRequestSentEvent)

    @Topic(FriendshipRequestAcceptedEvent.TOPIC) fun publish(event: FriendshipRequestAcceptedEvent)

    @Topic(NewMessageInFriendshipEvent.TOPIC) fun publish(event: NewMessageInFriendshipEvent)

    @Topic(FriendshipRequestRejectedEvent.TOPIC) fun publish(event: FriendshipRequestRejectedEvent)
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
