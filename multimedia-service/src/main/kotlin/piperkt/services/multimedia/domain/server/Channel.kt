package piperkt.services.multimedia.domain.server

import piperkt.common.Entity
import piperkt.common.UUIDEntityId
import piperkt.services.multimedia.domain.session.SessionId

class ChannelId(value: String = newId()) : UUIDEntityId(value)

class Channel(id: ChannelId, val sessionId: SessionId) : Entity<ChannelId>(id)
