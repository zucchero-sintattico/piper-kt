package piperkt.services.servers.presentation.json

import piperkt.services.servers.presentation.json.request.CreateServerRequest
import piperkt.services.servers.presentation.json.response.CreateServerResponse

interface JsonServerStub {
    fun createServer(request: CreateServerRequest): CreateServerResponse
}
