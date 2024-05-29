package piperkt.events

import piperkt.common.events.DomainEvent
import piperkt.common.events.EventPublisher
import kotlin.js.JsExport

@JsExport
sealed interface ServerEvent : DomainEvent

/**
 * Server created event.
 *
 * @param serverId The id of the server.
 * @param owner The owner of the server.
 */
@JsExport
data class ServerCreatedEvent(val serverId: String, val owner: String) : ServerEvent {
    companion object {
        const val TOPIC = "server-created"
    }
}

/**
 * Server updated event.
 *
 * @param serverId The id of the server.
 */
@JsExport
data class ServerUpdatedEvent(val serverId: String) : ServerEvent {
    companion object {
        const val TOPIC = "server-updated"
    }
}

/**
 * Server user added event.
 *
 * @param serverId The id of the server.
 * @param username The username of the user.
 */
@JsExport
data class ServerUserAddedEvent(val serverId: String, val username: String) : ServerEvent {
    companion object {
        const val TOPIC = "server-user-added"
    }
}

/**
 * Server user removed event.
 *
 * @param serverId The id of the server.
 * @param username The username of the user.
 */
@JsExport
data class ServerUserRemovedEvent(val serverId: String, val username: String) : ServerEvent {
    companion object {
        const val TOPIC = "server-user-removed"
    }
}

/**
 * Server user kicked event.
 *
 * @param serverId The id of the server.
 * @param username The username of the user.
 */
@JsExport
data class ServerUserKickedEvent(val serverId: String, val username: String) : ServerEvent {
    companion object {
        const val TOPIC = "server-user-kicked"
    }
}

/**
 * Server deleted event.
 *
 * @param serverId The id of the server.
 */
@JsExport
data class ServerDeletedEvent(val serverId: String) : ServerEvent {
    companion object {
        const val TOPIC = "server-deleted"
    }
}

interface ServerEventPublisher : EventPublisher<ServerEvent>
