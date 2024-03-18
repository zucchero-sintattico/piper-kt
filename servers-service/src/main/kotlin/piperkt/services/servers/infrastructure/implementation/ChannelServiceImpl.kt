package piperkt.services.servers.infrastructure.implementation

import jakarta.inject.Singleton
import piperkt.services.commons.domain.events.DomainEventPublisher
import piperkt.services.servers.application.ChannelRepository
import piperkt.services.servers.application.ChannelService
import piperkt.services.servers.application.ServerRepository

@Singleton
class ChannelServiceImpl(
    serverRepository: ServerRepository,
    channelRepository: ChannelRepository,
    eventPublisher: DomainEventPublisher
) : ChannelService(channelRepository, serverRepository, eventPublisher)
