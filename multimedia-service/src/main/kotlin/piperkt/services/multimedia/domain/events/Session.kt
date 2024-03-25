package piperkt.services.multimedia.domain.events

import piperkt.services.multimedia.domain.SessionId
import piperkt.services.multimedia.domain.User

sealed interface SessionEvent : DomainEvent {

    data class SessionCreated(val sessionId: SessionId, val allowedUsers: List<User>) :
        SessionEvent

    data class SessionDeleted(val sessionId: SessionId) : SessionEvent

    data class AllowedUserAdded(val sessionId: SessionId, val user: User) : SessionEvent

    data class AllowedUserRemoved(val sessionId: SessionId, val user: User) : SessionEvent

    data class ParticipantJoined(val sessionId: SessionId, val user: User) : SessionEvent

    data class ParticipantLeft(val sessionId: SessionId, val user: User) : SessionEvent
}

interface SessionEventPublisher : EventPublisher<SessionEvent>
