package piperkt.services.servers.infrastructure.implementation

import jakarta.inject.Singleton
import piperkt.services.commons.domain.events.DomainEventPublisher
import piperkt.services.servers.application.ServerRepository
import piperkt.services.servers.application.ServerService

@Singleton
class ServerServiceImpl(serverRepository: ServerRepository, eventPublisher: DomainEventPublisher) :
    ServerService(serverRepository, eventPublisher)
