package piperkt.services.servers.domain

import piperkt.services.commons.domain.id.ServerId

class Server(
    val id: ServerId,
    val name: String,
    val description: String = "",
    val owner: String,
    val users: List<String> = listOf(owner),
    val channels: List<Channel> = emptyList(),
)
