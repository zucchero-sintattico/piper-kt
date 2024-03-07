package piperkt.services.servers.presentation.json.request

data class CreateServerRequest(val name: String, val description: String, val owner: String)
