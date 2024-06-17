package piperkt.services.servers.infrastructure.implementation

import jakarta.inject.Singleton
import piperkt.events.ServerEventPublisher
import piperkt.services.servers.application.ServerService
import piperkt.services.servers.domain.ServerRepository

@Singleton
class ServerServiceImpl(serverRepository: ServerRepository, eventPublisher: ServerEventPublisher) :
    ServerService(serverRepository, eventPublisher)
