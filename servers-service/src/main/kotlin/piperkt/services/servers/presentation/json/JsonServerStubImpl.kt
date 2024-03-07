package piperkt.services.servers.presentation.json

import piperkt.services.servers.application.ServerService
import piperkt.services.servers.presentation.json.request.CreateServerRequest
import piperkt.services.servers.presentation.json.response.CreateServerResponse

open class JsonServerStubImpl(private val serverService: ServerService) : JsonServerStub {
    override fun createServer(request: CreateServerRequest): CreateServerResponse {
        return serverService.createServer(request.name, request.description, request.owner).let {
            CreateServerResponse(it.name, it.description)
        }
    }
}
