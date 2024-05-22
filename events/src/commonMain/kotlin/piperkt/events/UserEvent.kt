package piperkt.events

import piperkt.common.events.DomainEvent
import piperkt.common.events.EventPublisher
import kotlin.js.JsExport

/** User domain event. */
@JsExport sealed interface UserEvent : DomainEvent

/**
 * User created event.
 *
 * @param username The username of the user.
 * @param description The description of the user.
 * @param profilePicture The profile picture url of the user.
 */
@JsExport data class UserCreatedEvent(
    val username: String,
    val email: String? = null,
    val description: String? = null,
    val profilePicture: String? = null,
) : UserEvent

/**
 * User updated event.
 *
 * @param username The username of the user.
 * @param description The description of the user.
 * @param profilePicture The profile picture of the user.
 */
@JsExport data class UserUpdatedEvent(
    val username: String,
    val email: String? = null,
    val description: String? = null,
    val profilePicture: String? = null,
) : UserEvent

@JsExport data class UserLoggedInEvent(val username: String) : UserEvent

@JsExport data class UserLoggedOutEvent(val username: String) : UserEvent

@JsExport data class UserOnlineEvent(val username: String) : UserEvent

@JsExport data class UserOfflineEvent(val username: String) : UserEvent

interface UserEventPublisher : EventPublisher<UserEvent>
