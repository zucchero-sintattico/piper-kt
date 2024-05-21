package piperkt.events

import piperkt.common.events.DomainEvent
import piperkt.common.events.EventPublisher
import kotlin.js.JsExport

@JsExport sealed interface SessionEvent : DomainEvent

@JsExport data class SessionCreated(val sessionId: String, val allowedUsers: Set<String>) : SessionEvent

@JsExport data class SessionDeleted(val sessionId: String) : SessionEvent

@JsExport data class AllowedUserAdded(val sessionId: String, val username: String) : SessionEvent

@JsExport data class AllowedUserRemoved(val sessionId: String, val user: String) : SessionEvent

@JsExport data class ParticipantJoined(val sessionId: String, val user: String) : SessionEvent

@JsExport data class ParticipantLeft(val sessionId: String, val user: String) : SessionEvent

interface SessionEventPublisher : EventPublisher<SessionEvent>
