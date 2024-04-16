package piperkt.services.users.infrastructure.events.publishers

import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.Topic
import piperkt.services.users.domain.user.UserEvent
import piperkt.services.users.domain.user.UserEventPublisher

@KafkaClient
interface UserEventKafkaPublisher : UserEventPublisher {
    override fun publish(event: UserEvent) {
        when (event) {
            is UserEvent.UserCreated -> onUserCreated(event)
            is UserEvent.UserUpdated -> onUserUpdated(event)
            is UserEvent.UserLoggedIn -> this.onUserLoggedIn(event)
            is UserEvent.UserLoggedOut -> this.onUserLoggedOut(event)
            is UserEvent.FriendRequestSent -> this.onFriendRequestSent(event)
            is UserEvent.FriendRequestAccepted -> this.onFriendRequestAccepted(event)
            is UserEvent.FriendRequestRejected -> this.onFriendRequestRejected(event)
        }
    }

    @Topic("user-created") fun onUserCreated(event: UserEvent.UserCreated)

    @Topic("user-updated") fun onUserUpdated(event: UserEvent.UserUpdated)

    @Topic("user-logged-in") fun onUserLoggedIn(event: UserEvent.UserLoggedIn)

    @Topic("user-logged-out") fun onUserLoggedOut(event: UserEvent.UserLoggedOut)

    @Topic("friend-request-sent") fun onFriendRequestSent(event: UserEvent.FriendRequestSent)

    @Topic("friend-request-accepted")
    fun onFriendRequestAccepted(event: UserEvent.FriendRequestAccepted)

    @Topic("friend-request-rejected")
    fun onFriendRequestRejected(event: UserEvent.FriendRequestRejected)
}
