package piperkt.services.servers.infrastructure.events

import io.micronaut.serde.annotation.SerdeImport
import piperkt.common.events.ChannelEvent
import piperkt.common.events.ServerEvent

@SerdeImport(ServerEvent::class) object SerializableServerEvents

@SerdeImport(ChannelEvent::class) object SerializableChannelEvents
