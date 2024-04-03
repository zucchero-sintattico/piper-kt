package piperkt.services.multimedia.infrastructure.events.listeners

import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.Topic
import piperkt.services.multimedia.application.listeners.UserEventsListener
import piperkt.services.multimedia.domain.user.UserEvent

@KafkaListener
class UserEventsKafkaListener(private val userEventsListener: UserEventsListener) {
    @Topic("user-joined-server")
    fun onUserJoinedServer(event: UserEvent.UserJoinedServer) = userEventsListener.handle(event)

    @Topic("user-left-server")
    fun onUserLeftServer(event: UserEvent.UserLeftServer) = userEventsListener.handle(event)

    @Topic("user-kicked-from-server")
    fun onUserKickedFromServer(event: UserEvent.UserKickedFromServer) =
        userEventsListener.handle(event)
}
