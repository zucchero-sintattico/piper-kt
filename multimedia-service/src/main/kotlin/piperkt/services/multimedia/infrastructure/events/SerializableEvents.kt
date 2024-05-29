package piperkt.services.multimedia.infrastructure.events

import io.micronaut.serde.annotation.SerdeImport
import piperkt.events.AllowedUserAddedEvent
import piperkt.events.AllowedUserRemovedEvent
import piperkt.events.ChannelCreatedEvent
import piperkt.events.ChannelDeletedEvent
import piperkt.events.ChannelUpdatedEvent
import piperkt.events.FriendshipRequestAcceptedEvent
import piperkt.events.MessageInChannelEvent
import piperkt.events.ParticipantJoinedEvent
import piperkt.events.ParticipantLeftEvent
import piperkt.events.ServerCreatedEvent
import piperkt.events.ServerDeletedEvent
import piperkt.events.ServerUserAddedEvent
import piperkt.events.ServerUserKickedEvent
import piperkt.events.ServerUserRemovedEvent
import piperkt.events.SessionCreatedEvent
import piperkt.events.SessionDeletedEvent

/**
 * This object is used to import all the events that can be serialized. This is necessary to avoid
 * reflection issues when serializing and deserializing events.
 */
@SerdeImport(ChannelCreatedEvent::class)
@SerdeImport(ChannelDeletedEvent::class)
@SerdeImport(ChannelUpdatedEvent::class)
@SerdeImport(MessageInChannelEvent::class)
@SerdeImport(FriendshipRequestAcceptedEvent::class)
@SerdeImport(ServerCreatedEvent::class)
@SerdeImport(ServerDeletedEvent::class)
@SerdeImport(ServerUserAddedEvent::class)
@SerdeImport(ServerUserRemovedEvent::class)
@SerdeImport(ServerUserKickedEvent::class)
@SerdeImport(SessionCreatedEvent::class)
@SerdeImport(SessionDeletedEvent::class)
@SerdeImport(AllowedUserAddedEvent::class)
@SerdeImport(AllowedUserRemovedEvent::class)
@SerdeImport(ParticipantJoinedEvent::class)
@SerdeImport(ParticipantLeftEvent::class)
object SerializableEvents
