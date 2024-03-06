package piperkt.services.servers.application.request

data class CreateServerRequest(
    val name: String,
    val description: String,
    val owner: String,
)
