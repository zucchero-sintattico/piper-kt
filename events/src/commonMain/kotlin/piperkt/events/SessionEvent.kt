package piperkt.events

import piperkt.common.events.DomainEvent
import piperkt.common.events.EventPublisher

sealed interface SessionEvent : DomainEvent {

    data class SessionCreated(val sessionId: String, val allowedUsers: Set<String>) : SessionEvent

    data class SessionDeleted(val sessionId: String) : SessionEvent

    data class AllowedUserAdded(val sessionId: String, val username: String) : SessionEvent

    data class AllowedUserRemoved(val sessionId: String, val user: String) : SessionEvent

    data class ParticipantJoined(val sessionId: String, val user: String) : SessionEvent

    data class ParticipantLeft(val sessionId: String, val user: String) : SessionEvent
}

interface SessionEventPublisher : EventPublisher<SessionEvent>
