package piperkt.services.servers.interfaces.web.api.dto

import io.micronaut.serde.annotation.Serdeable
import piperkt.services.servers.domain.Channel
import piperkt.services.servers.domain.ChannelType

@Serdeable
data class ChannelDTO(
    val id: String,
    val name: String,
    val type: String,
    var description: String,
    val messages: MutableList<MessageDTO> = mutableListOf()
) {
    companion object {
        fun fromDomain(channel: Channel): ChannelDTO {
            return ChannelDTO(
                id = channel.id.toString(),
                name = channel.name,
                type = channel.type.toString(),
                description = channel.description,
                messages = channel.messages.map { MessageDTO.fromDomain(it) }.toMutableList()
            )
        }
    }
}

fun ChannelDTO.toDomain(): Channel {
    return Channel(name = name, type = ChannelType.valueOf(type), description = description)
}
