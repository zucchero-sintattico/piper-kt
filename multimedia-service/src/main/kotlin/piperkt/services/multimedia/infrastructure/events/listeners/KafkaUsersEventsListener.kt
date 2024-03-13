package piperkt.services.multimedia.infrastructure.events.listeners

import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.Topic
import piperkt.services.multimedia.application.users.UsersEvents
import piperkt.services.multimedia.application.users.UsersEventsListener
import piperkt.services.multimedia.application.users.events.UserJoinedServer
import piperkt.services.multimedia.application.users.events.UserKickedFromServer
import piperkt.services.multimedia.application.users.events.UserLeftServer

@KafkaListener(
    clientId = "multimedia-service-users-events-listener",
    groupId = "multimedia-service-users-events-listener"
)
class KafkaUsersEventsListener(private val usersEventsListener: UsersEventsListener) : UsersEvents {

    @Topic("user-joined-server")
    override fun onUserJoinedServer(event: UserJoinedServer) {
        usersEventsListener.onUserJoinedServer(event)
    }

    @Topic("user-left-server")
    override fun onUserLeftServer(event: UserLeftServer) {
        usersEventsListener.onUserLeftServer(event)
    }

    @Topic("user-kicked-from-server")
    override fun onUserKickedFromServer(event: UserKickedFromServer) {
        usersEventsListener.onUserKickedFromServer(event)
    }
}
