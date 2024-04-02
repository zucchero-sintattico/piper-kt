package piperkt.services.multimedia.domain.events

import piperkt.services.multimedia.domain.session.SessionId
import piperkt.services.multimedia.domain.user.UserId

sealed interface SessionEvent : DomainEvent {

    data class SessionCreated(val sessionId: SessionId, val allowedUsers: List<UserId>) :
        SessionEvent

    data class SessionDeleted(val sessionId: SessionId) : SessionEvent

    data class AllowedUserAdded(val sessionId: SessionId, val userId: UserId) : SessionEvent

    data class AllowedUserRemoved(val sessionId: SessionId, val user: UserId) : SessionEvent

    data class ParticipantJoined(val sessionId: SessionId, val user: UserId) : SessionEvent

    data class ParticipantLeft(val sessionId: SessionId, val user: UserId) : SessionEvent
}

interface SessionEventPublisher : EventPublisher<SessionEvent>
