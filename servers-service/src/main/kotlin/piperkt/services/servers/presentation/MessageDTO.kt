package piperkt.services.servers.presentation

import io.micronaut.serde.annotation.Serdeable
import piperkt.services.servers.domain.Message
import piperkt.services.servers.domain.MessageId

@Serdeable
data class MessageDTO(
    val id: String,
    val sender: String,
    val content: String,
    val timestamp: String = java.time.Instant.now().toString(),
) {
    fun toDomain(): Message {
        return Message(
            id = MessageId(id),
            sender = sender,
            content = content,
            timestamp = java.time.Instant.parse(timestamp)
        )
    }

    companion object {
        fun fromDomain(message: Message): MessageDTO {
            return MessageDTO(
                id = message.id.value,
                sender = message.sender,
                content = message.content,
                timestamp = message.timestamp.toString()
            )
        }
    }
}
