package piperkt.services.servers.infrastructure.implementation

import jakarta.inject.Singleton
import piperkt.common.events.ChannelEventPublisher
import piperkt.services.servers.application.ChannelService
import piperkt.services.servers.application.ServerRepository

@Singleton
class ChannelServiceImpl(
    serverRepository: ServerRepository,
    eventPublisher: ChannelEventPublisher
) : ChannelService(serverRepository, eventPublisher)
