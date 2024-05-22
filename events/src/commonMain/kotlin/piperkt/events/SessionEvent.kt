package piperkt.events

import piperkt.common.events.DomainEvent
import piperkt.common.events.EventPublisher
import kotlin.js.JsExport

@JsExport sealed interface SessionEvent : DomainEvent

@JsExport data class SessionCreatedEvent(val sessionId: String, val allowedUsers: Set<String>) : SessionEvent

@JsExport data class SessionDeletedEvent(val sessionId: String) : SessionEvent

@JsExport data class AllowedUserAddedEvent(val sessionId: String, val username: String) : SessionEvent

@JsExport data class AllowedUserRemovedEvent(val sessionId: String, val user: String) : SessionEvent

@JsExport data class ParticipantJoinedEvent(val sessionId: String, val user: String) : SessionEvent

@JsExport data class ParticipantLeftEvent(val sessionId: String, val user: String) : SessionEvent

interface SessionEventPublisher : EventPublisher<SessionEvent>
