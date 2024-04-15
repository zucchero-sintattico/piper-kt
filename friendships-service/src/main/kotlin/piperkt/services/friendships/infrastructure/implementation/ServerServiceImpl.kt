package piperkt.services.friendships.infrastructure.implementation

import jakarta.inject.Singleton
import piperkt.common.events.ServerEventPublisher
import piperkt.services.friendships.application.ServerRepository
import piperkt.services.friendships.application.ServerService

@Singleton
class ServerServiceImpl(serverRepository: ServerRepository, eventPublisher: ServerEventPublisher) :
    ServerService(serverRepository, eventPublisher)
