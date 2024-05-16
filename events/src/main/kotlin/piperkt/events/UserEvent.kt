package piperkt.events

import piperkt.common.events.DomainEvent
import piperkt.common.events.EventPublisher

/** User domain event. */
sealed interface UserEvent : DomainEvent {

    /**
     * User created event.
     *
     * @param username The username of the user.
     * @param description The description of the user.
     * @param profilePicture The profile picture url of the user.
     */
    data class UserCreated(
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
    data class UserUpdated(
        val username: String,
        val email: String? = null,
        val description: String? = null,
        val profilePicture: String? = null,
    ) : UserEvent
}

interface UserEventPublisher : EventPublisher<UserEvent>