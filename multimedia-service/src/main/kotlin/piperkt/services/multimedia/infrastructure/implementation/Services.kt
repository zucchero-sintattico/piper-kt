package piperkt.services.multimedia.infrastructure.implementation

import jakarta.inject.Singleton
import piperkt.services.multimedia.application.MultimediaService
import piperkt.services.multimedia.domain.channels.ChannelRepository
import piperkt.services.multimedia.domain.directs.DirectRepository
import piperkt.services.multimedia.domain.sessions.SessionRepository

object Services {
    @Singleton
    class MultimediaServiceImpl(
        directRepository: DirectRepository,
        sessionRepository: SessionRepository,
        channelRepository: ChannelRepository
    ) : MultimediaService(directRepository, sessionRepository, channelRepository)
}
