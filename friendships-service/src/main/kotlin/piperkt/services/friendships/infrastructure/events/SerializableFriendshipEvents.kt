package piperkt.services.friendships.infrastructure.events

import io.micronaut.serde.annotation.SerdeImport
import piperkt.events.FriendshipRequestAcceptedEvent
import piperkt.events.FriendshipRequestRejectedEvent
import piperkt.events.FriendshipRequestSentEvent
import piperkt.events.NewMessageInFriendshipEvent

@SerdeImport(FriendshipRequestSentEvent::class)
@SerdeImport(FriendshipRequestAcceptedEvent::class)
@SerdeImport(FriendshipRequestRejectedEvent::class)
@SerdeImport(NewMessageInFriendshipEvent::class)
object SerializableFriendshipEvents
