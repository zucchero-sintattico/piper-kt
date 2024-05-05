package piperkt.services.friendships.infrastructure.events

import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.Topic
import piperkt.common.events.FriendshipEvent
import piperkt.common.events.FriendshipEventPublisher

@KafkaClient
abstract class KafkaFriendshipEventPublisher : FriendshipEventPublisher {

    override fun publish(event: FriendshipEvent) {
        when (event) {
            is FriendshipEvent.FriendshipRequestAcceptedEvent -> publish(event)
            is FriendshipEvent.FriendshipRequestSentEvent -> publish(event)
            is FriendshipEvent.NewMessageInFriendshipEvent -> publish(event)
            is FriendshipEvent.FriendshipRequestRejectedEvent -> publish(event)
        }
    }

    @Topic("friendship-events")
    abstract fun publish(event: FriendshipEvent.FriendshipRequestSentEvent)

    @Topic("friendship-events")
    abstract fun publish(event: FriendshipEvent.FriendshipRequestAcceptedEvent)

    @Topic("friendship-events")
    abstract fun publish(event: FriendshipEvent.NewMessageInFriendshipEvent)

    @Topic("friendship-events")
    abstract fun publish(event: FriendshipEvent.FriendshipRequestRejectedEvent)
}
