package piperkt.services.servers.interfaces.web.api.dto

import io.micronaut.serde.annotation.Serdeable
import piperkt.services.servers.domain.Server

@Serdeable
data class ServerDTO(
    val id: String,
    val name: String,
    val description: String = "",
    val owner: String,
    val users: MutableList<String> = mutableListOf(owner),
    val channels: MutableList<ChannelDTO> = mutableListOf(),
) {
    companion object {
        fun fromDomain(server: Server): ServerDTO {
            return ServerDTO(
                id = server.id.toString(),
                name = server.name,
                description = server.description,
                owner = server.owner,
                users = server.users,
                channels = server.channels.map { ChannelDTO.fromDomain(it) }.toMutableList()
            )
        }
    }
}

fun ServerDTO.toDomain(): Server {
    return Server(
        name = name,
        description = description,
        owner = owner,
        users = users,
        channels = channels.map { it.toDomain() }.toMutableList()
    )
}
