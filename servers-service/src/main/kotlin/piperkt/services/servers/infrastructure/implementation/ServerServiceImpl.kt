package piperkt.services.servers.infrastructure.implementation

import jakarta.inject.Singleton
import piperkt.common.events.ServerEventPublisher
import piperkt.services.servers.application.ServerRepository
import piperkt.services.servers.application.ServerService

@Singleton
class ServerServiceImpl(serverRepository: ServerRepository, eventPublisher: ServerEventPublisher) :
    ServerService(serverRepository, eventPublisher)
