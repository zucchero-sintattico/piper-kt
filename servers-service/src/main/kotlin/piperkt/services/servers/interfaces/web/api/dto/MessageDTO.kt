package piperkt.services.servers.interfaces.web.api.dto

import piperkt.common.id.MessageId
import piperkt.services.servers.domain.Message

data class MessageDTO(
    val id: String,
    val sender: String,
    val content: String,
    val timestamp: String,
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
                id = message.id.toString(),
                sender = message.sender,
                content = message.content,
                timestamp = message.timestamp.toString()
            )
        }
    }
}


