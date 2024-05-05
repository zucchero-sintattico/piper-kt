package piperkt.services.friendships.infrastructure.events

import io.micronaut.serde.annotation.SerdeImport
import piperkt.common.events.FriendshipEvent

@SerdeImport(FriendshipEvent::class) object SerializableFrienshipEvents
