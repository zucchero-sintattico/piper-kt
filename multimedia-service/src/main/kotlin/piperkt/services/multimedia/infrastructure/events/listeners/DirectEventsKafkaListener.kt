package piperkt.services.multimedia.infrastructure.events.listeners

import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.Topic
import piperkt.services.multimedia.application.listeners.DirectEventsListener
import piperkt.services.multimedia.domain.direct.DirectEvent

@KafkaListener
class DirectEventsKafkaListener(private val directEventsListener: DirectEventsListener) {
    @Topic("friend-request-accepted")
    fun onFriendRequestAccepted(event: DirectEvent.FriendRequestAccepted) =
        directEventsListener.handle(event)
}
