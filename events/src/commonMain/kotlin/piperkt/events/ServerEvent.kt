package piperkt.events

import piperkt.common.events.DomainEvent
import piperkt.common.events.EventPublisher
import kotlin.js.JsExport

@JsExport sealed interface ServerEvent : DomainEvent

@JsExport data class ServerCreatedEvent(val serverId: String, val owner: String) : ServerEvent {
    companion object {
        const val TOPIC = "server-created"
    }
}

@JsExport data class ServerUpdatedEvent(val serverId: String) : ServerEvent {
    companion object {
        const val TOPIC = "server-updated"
    }
}

@JsExport data class ServerUserAddedEvent(val serverId: String, val username: String) : ServerEvent {
    companion object {
        const val TOPIC = "server-user-added"
    }
}

@JsExport data class ServerUserRemovedEvent(val serverId: String, val username: String) : ServerEvent {
    companion object {
        const val TOPIC = "server-user-removed"
    }
}

@JsExport data class ServerUserKickedEvent(val serverId: String, val username: String) : ServerEvent {
    companion object {
        const val TOPIC = "server-user-kicked"
    }
}

@JsExport data class ServerDeletedEvent(val serverId: String) : ServerEvent {
    companion object {
        const val TOPIC = "server-deleted"
    }
}

interface ServerEventPublisher : EventPublisher<ServerEvent>
