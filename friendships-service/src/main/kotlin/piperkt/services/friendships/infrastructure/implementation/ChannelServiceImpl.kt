package piperkt.services.friendships.infrastructure.implementation

import jakarta.inject.Singleton
import piperkt.common.events.ChannelEventPublisher
import piperkt.services.friendships.application.ChannelService
import piperkt.services.friendships.application.ServerRepository

@Singleton
class ChannelServiceImpl(
    serverRepository: ServerRepository,
    eventPublisher: ChannelEventPublisher
) : ChannelService(serverRepository, eventPublisher)
