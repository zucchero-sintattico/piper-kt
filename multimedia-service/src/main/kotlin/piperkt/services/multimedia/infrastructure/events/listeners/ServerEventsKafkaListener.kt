package piperkt.services.multimedia.infrastructure.events.listeners

import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.Topic
import piperkt.services.multimedia.application.listeners.ServerEventsListener
import piperkt.services.multimedia.domain.server.ServerEvent

@KafkaListener
class ServerEventsKafkaListener(private val serverEventsListener: ServerEventsListener) {
    @Topic("server-created")
    fun onServerCreated(event: ServerEvent.ServerCreated) = serverEventsListener.handle(event)

    @Topic("server-deleted")
    fun onServerDeleted(event: ServerEvent.ServerDeleted) = serverEventsListener.handle(event)

    @Topic("user-joined-server")
    fun onUserJoinedServer(event: ServerEvent.UserJoinedServer) = serverEventsListener.handle(event)

    @Topic("user-left-server")
    fun onUserLeftServer(event: ServerEvent.UserLeftServer) = serverEventsListener.handle(event)

    @Topic("user-kicked-from-server")
    fun onUserKickedFromServer(event: ServerEvent.UserKickedFromServer) =
        serverEventsListener.handle(event)
}
