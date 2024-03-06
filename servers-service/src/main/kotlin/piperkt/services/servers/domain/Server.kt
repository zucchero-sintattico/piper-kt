package piperkt.services.servers.domain

import piperkt.services.servers.commons.id.ServerId

class Server(
    val id: ServerId,
    val name: String,
    val description: String = "",
    val owner: String,
    val members: List<String> = listOf(owner),
    val channels: List<Channel> = emptyList(),
)
