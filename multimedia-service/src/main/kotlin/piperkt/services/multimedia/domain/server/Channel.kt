package piperkt.services.multimedia.domain.server

import piperkt.common.Entity
import piperkt.services.multimedia.domain.session.SessionId

class Channel(id: ChannelId, val name: String, val sessionId: SessionId) : Entity<ChannelId>(id)
