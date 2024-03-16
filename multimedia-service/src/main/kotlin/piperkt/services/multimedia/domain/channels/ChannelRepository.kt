package piperkt.services.multimedia.domain.channels

import piperkt.services.multimedia.domain.sessions.Session

interface ChannelRepository {
    fun getSessionInChannel(channelId: ChannelId): Session?
}
