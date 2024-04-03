package piperkt.services.multimedia.domain.session

import piperkt.common.DomainEvent
import piperkt.common.EventPublisher
import piperkt.services.multimedia.domain.user.Username

sealed interface SessionEvent : DomainEvent {

    data class SessionCreated(val sessionId: SessionId, val allowedUsers: List<Username>) :
        SessionEvent

    data class SessionDeleted(val sessionId: SessionId) : SessionEvent

    data class AllowedUserAdded(val sessionId: SessionId, val username: Username) : SessionEvent

    data class AllowedUserRemoved(val sessionId: SessionId, val user: Username) : SessionEvent

    data class ParticipantJoined(val sessionId: SessionId, val user: Username) : SessionEvent

    data class ParticipantLeft(val sessionId: SessionId, val user: Username) : SessionEvent
}

interface SessionEventPublisher : EventPublisher<SessionEvent>
