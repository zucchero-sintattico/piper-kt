package piperkt.services.servers.infrastructure.events

import io.micronaut.serde.annotation.SerdeImport
import piperkt.common.events.ServerEvent

@SerdeImport(ServerEvent::class) object SerializableEvents
