package piperkt.services.servers.presentation

import io.micronaut.serde.annotation.Serdeable
import piperkt.services.servers.domain.Channel
import piperkt.services.servers.domain.ChannelType

@Serdeable
data class ChannelDTO(
    val id: String,
    val name: String,
    val type: String,
    var description: String,
    val messages: MutableList<ChannelMessageDTO> = mutableListOf()
) {
    fun toDomain(): Channel {
        return Channel(name = name, type = ChannelType.valueOf(type), description = description)
    }

    companion object {
        fun fromDomain(channel: Channel): ChannelDTO {
            return ChannelDTO(
                id = channel.id.value,
                name = channel.name,
                type = channel.type.toString(),
                description = channel.description,
                messages =
                    channel.channelMessages.map { ChannelMessageDTO.fromDomain(it) }.toMutableList()
            )
        }
    }
}
