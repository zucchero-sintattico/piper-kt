package piperkt.services.friendships.infrastructure.events

import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.Topic
import jakarta.inject.Singleton
import piperkt.common.events.FriendshipEvent
import piperkt.common.events.FriendshipEventPublisher

@KafkaClient
interface KafkaFriendshipEventPublisher {

    @Topic("friendship-events") fun publish(event: FriendshipEvent.FriendshipRequestSentEvent)

    @Topic("friendship-events") fun publish(event: FriendshipEvent.FriendshipRequestAcceptedEvent)

    @Topic("friendship-events") fun publish(event: FriendshipEvent.NewMessageInFriendshipEvent)

    @Topic("friendship-events") fun publish(event: FriendshipEvent.FriendshipRequestRejectedEvent)
}

@Singleton
class FriendshipEventPublisherImpl(
    private val kafkaFriendshipEventPublisher: KafkaFriendshipEventPublisher
) : FriendshipEventPublisher {
    override fun publish(event: FriendshipEvent) {
        when (event) {
            is FriendshipEvent.FriendshipRequestAcceptedEvent ->
                kafkaFriendshipEventPublisher.publish(event)
            is FriendshipEvent.FriendshipRequestSentEvent ->
                kafkaFriendshipEventPublisher.publish(event)
            is FriendshipEvent.NewMessageInFriendshipEvent ->
                kafkaFriendshipEventPublisher.publish(event)
            is FriendshipEvent.FriendshipRequestRejectedEvent ->
                kafkaFriendshipEventPublisher.publish(event)
        }
    }
}
