package piperkt.services.multimedia.domain.channels

import piperkt.services.multimedia.domain.sessions.Session

class Channel(val id: ChannelId, val name: String, val session: Session)
