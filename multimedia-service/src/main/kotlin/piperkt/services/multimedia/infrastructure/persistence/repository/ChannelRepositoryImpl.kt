package piperkt.services.multimedia.infrastructure.persistence.repository

import jakarta.inject.Singleton
import piperkt.services.multimedia.domain.channels.ChannelId
import piperkt.services.multimedia.domain.channels.ChannelRepository
import piperkt.services.multimedia.domain.sessions.Session

@Singleton
class ChannelRepositoryImpl : ChannelRepository {
    override fun getSessionInChannel(channelId: ChannelId): Session {
        throw UnsupportedOperationException("Not implemented")
    }
}
