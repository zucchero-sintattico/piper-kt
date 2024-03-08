package piperkt.services.servers.presentation.json

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class ServerJson(
    val id: String,
    val name: String,
    val description: String,
    val owner: String,
    val users: List<String>,
)
