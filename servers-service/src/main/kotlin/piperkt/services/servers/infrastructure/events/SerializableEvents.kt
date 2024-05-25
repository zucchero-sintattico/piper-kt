package piperkt.services.servers.infrastructure.events

import io.micronaut.serde.annotation.SerdeImport
import piperkt.events.ChannelCreatedEvent
import piperkt.events.ChannelDeletedEvent
import piperkt.events.ChannelUpdatedEvent
import piperkt.events.MessageInChannelEvent
import piperkt.events.ServerCreatedEvent
import piperkt.events.ServerDeletedEvent
import piperkt.events.ServerUpdatedEvent
import piperkt.events.ServerUserAddedEvent
import piperkt.events.ServerUserKickedEvent
import piperkt.events.ServerUserRemovedEvent

@SerdeImport(ServerCreatedEvent::class)
@SerdeImport(ServerUpdatedEvent::class)
@SerdeImport(ServerUserAddedEvent::class)
@SerdeImport(ServerUserRemovedEvent::class)
@SerdeImport(ServerUserKickedEvent::class)
@SerdeImport(ServerDeletedEvent::class)
object SerializableServerEvents

@SerdeImport(ChannelCreatedEvent::class)
@SerdeImport(ChannelDeletedEvent::class)
@SerdeImport(ChannelUpdatedEvent::class)
@SerdeImport(MessageInChannelEvent::class)
object SerializableChannelEvents
