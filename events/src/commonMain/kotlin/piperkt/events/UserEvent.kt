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
) : UserEvent {
    companion object {
        const val TOPIC = "user-created"
    }
}

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
) : UserEvent {
    companion object {
        const val TOPIC = "user-updated"
    }
}

@JsExport data class UserLoggedInEvent(val username: String) : UserEvent {
    companion object {
        const val TOPIC = "user-logged-in"
    }
}

@JsExport data class UserLoggedOutEvent(val username: String) : UserEvent {
    companion object {
        const val TOPIC = "user-logged-out"
    }
}

@JsExport data class UserOnlineEvent(val username: String) : UserEvent {
    companion object {
        const val TOPIC = "user-online"
    }
}

@JsExport data class UserOfflineEvent(val username: String) : UserEvent {
    companion object {
        const val TOPIC = "user-offline"
    }
}

interface UserEventPublisher : EventPublisher<UserEvent>
