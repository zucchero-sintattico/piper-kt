package piperkt.events

import piperkt.common.events.DomainEvent
import piperkt.common.events.EventPublisher
import kotlin.js.JsExport

@JsExport
sealed interface SessionEvent : DomainEvent

/**
 * Session created event.
 *
 * @param sessionId The session id.
 * @param allowedUsers The users allowed to join the session.
 */
@JsExport
data class SessionCreatedEvent(val sessionId: String, val allowedUsers: Set<String>) : SessionEvent {
    companion object {
        const val TOPIC = "session-created"
    }
}

/**
 * Session deleted event.
 *
 * @param sessionId The session id.
 */
@JsExport
data class SessionDeletedEvent(val sessionId: String) : SessionEvent {
    companion object {
        const val TOPIC = "session-deleted"
    }
}

/**
 * Allowed user added event.
 *
 * @param sessionId The session id.
 * @param username The username of the user.
 */
@JsExport
data class AllowedUserAddedEvent(val sessionId: String, val username: String) : SessionEvent {
    companion object {
        const val TOPIC = "allowed-user-added"
    }
}

/**
 * Allowed user removed event.
 *
 * @param sessionId The session id.
 * @param user The username of the user.
 */
@JsExport
data class AllowedUserRemovedEvent(val sessionId: String, val user: String) : SessionEvent {
    companion object {
        const val TOPIC = "allowed-user-removed"
    }
}

/**
 * Participant joined event.
 *
 * @param sessionId The session id.
 * @param user The username of the user.
 */
@JsExport
data class ParticipantJoinedEvent(val sessionId: String, val user: String) : SessionEvent {
    companion object {
        const val TOPIC = "participant-joined"
    }
}

/**
 * Participant left event.
 *
 * @param sessionId The session id.
 * @param user The username of the user.
 */
@JsExport
data class ParticipantLeftEvent(val sessionId: String, val user: String) : SessionEvent {
    companion object {
        const val TOPIC = "participant-left"
    }
}

interface SessionEventPublisher : EventPublisher<SessionEvent>
