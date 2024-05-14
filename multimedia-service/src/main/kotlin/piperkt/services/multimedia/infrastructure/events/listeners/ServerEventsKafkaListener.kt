package piperkt.services.multimedia.infrastructure.events.listeners

import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.Topic
import piperkt.common.events.ServerEvent
import piperkt.services.multimedia.application.server.ServerEventsListener

@KafkaListener
class ServerEventsKafkaListener(private val serverEventsListener: ServerEventsListener) {
    @Topic("server-created")
    fun onServerCreated(event: ServerEvent.ServerCreatedEvent) = serverEventsListener.handle(event)

    @Topic("server-deleted")
    fun onServerDeleted(event: ServerEvent.ServerDeletedEvent) = serverEventsListener.handle(event)

    @Topic("user-joined-server")
    fun onUserJoinedServer(event: ServerEvent.ServerUserAddedEvent) =
        serverEventsListener.handle(event)

    @Topic("user-left-server")
    fun onUserLeftServer(event: ServerEvent.ServerUserRemovedEvent) =
        serverEventsListener.handle(event)

    @Topic("user-kicked-from-server")
    fun onUserKickedFromServer(event: ServerEvent.ServerUserKickedEvent) =
        serverEventsListener.handle(event)
}
