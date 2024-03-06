package piperkt.services.servers.infrastructure.implementation

import jakarta.inject.Singleton
import piperkt.services.servers.application.ServerService
import piperkt.services.servers.domain.ServerRepository

@Singleton
class ServerServiceImpl(serverRepository: ServerRepository /*eventPublisher: EventPublisher*/) :
    ServerService(serverRepository /*, eventPublisher*/)
