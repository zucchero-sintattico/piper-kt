package piperkt.services.servers.infrastructure.events

import io.micronaut.serde.annotation.SerdeImport
import piperkt.events.ChannelEvent
import piperkt.events.ServerEvent

@SerdeImport(ServerEvent::class) object SerializableServerEvents

@SerdeImport(ChannelEvent::class) object SerializableChannelEvents
