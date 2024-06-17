package piperkt.services.servers.infrastructure.implementation

import jakarta.inject.Singleton
import piperkt.events.ChannelEventPublisher
import piperkt.services.servers.application.ChannelService
import piperkt.services.servers.domain.ServerRepository

@Singleton
class ChannelServiceImpl(
    serverRepository: ServerRepository,
    eventPublisher: ChannelEventPublisher,
) : ChannelService(serverRepository, eventPublisher)
