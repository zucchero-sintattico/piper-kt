package piperkt.services.servers.infrastructure.implementation

import jakarta.inject.Singleton
import piperkt.services.servers.application.ServerService
import piperkt.services.servers.presentation.json.JsonServerStubImpl

@Singleton
class MicronautJsonServerStubImpl(private val serverService: ServerService) :
    JsonServerStubImpl(serverService)
