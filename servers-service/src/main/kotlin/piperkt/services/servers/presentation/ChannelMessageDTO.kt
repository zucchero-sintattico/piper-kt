package piperkt.services.servers.presentation

import io.micronaut.serde.annotation.Serdeable
import piperkt.services.servers.domain.ChannelMessage
import piperkt.services.servers.domain.ChannelMessageId

@Serdeable
data class ChannelMessageDTO(
    val id: String,
    val sender: String,
    val content: String,
    val timestamp: String = java.time.Instant.now().toString(),
) {
    fun toDomain(): ChannelMessage {
        return ChannelMessage(
            id = ChannelMessageId(id),
            sender = sender,
            content = content,
            timestamp = java.time.Instant.parse(timestamp)
        )
    }

    companion object {
        fun fromDomain(channelMessage: ChannelMessage): ChannelMessageDTO {
            return ChannelMessageDTO(
                id = channelMessage.id.value,
                sender = channelMessage.sender,
                content = channelMessage.content,
                timestamp = channelMessage.timestamp.toString()
            )
        }
    }
}
