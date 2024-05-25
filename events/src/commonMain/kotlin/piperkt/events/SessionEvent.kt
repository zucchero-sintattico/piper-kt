package piperkt.events

import piperkt.common.events.DomainEvent
import piperkt.common.events.EventPublisher
import kotlin.js.JsExport

@JsExport sealed interface SessionEvent : DomainEvent

@JsExport data class SessionCreatedEvent(val sessionId: String, val allowedUsers: Set<String>) : SessionEvent {
    companion object {
        const val TOPIC = "session-created"
    }
}

@JsExport data class SessionDeletedEvent(val sessionId: String) : SessionEvent {
    companion object {
        const val TOPIC = "session-deleted"
    }
}

@JsExport data class AllowedUserAddedEvent(val sessionId: String, val username: String) : SessionEvent {
    companion object {
        const val TOPIC = "allowed-user-added"
    }
}

@JsExport data class AllowedUserRemovedEvent(val sessionId: String, val user: String) : SessionEvent {
    companion object {
        const val TOPIC = "allowed-user-removed"
    }
}

@JsExport data class ParticipantJoinedEvent(val sessionId: String, val user: String) : SessionEvent {
    companion object {
        const val TOPIC = "participant-joined"
    }
}

@JsExport data class ParticipantLeftEvent(val sessionId: String, val user: String) : SessionEvent {
companion object {
        const val TOPIC = "participant-left"
    }
}

interface SessionEventPublisher : EventPublisher<SessionEvent>
