package piperkt.services.friendships.infrastructure.events

import io.micronaut.serde.annotation.SerdeImport
import piperkt.events.FriendshipEvent

@SerdeImport(FriendshipEvent::class) object SerializableFriendshipEvents
