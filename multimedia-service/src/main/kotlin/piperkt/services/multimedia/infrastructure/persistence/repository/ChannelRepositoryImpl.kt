package piperkt.services.multimedia.infrastructure.persistence.repository

import jakarta.inject.Singleton
import piperkt.services.multimedia.domain.ChannelId
import piperkt.services.multimedia.domain.ChannelRepository
import piperkt.services.multimedia.domain.Session

@Singleton
class ChannelRepositoryImpl : ChannelRepository {
    override fun getSessionInChannel(channelId: ChannelId): Session {
        throw UnsupportedOperationException("Not implemented")
    }
}
