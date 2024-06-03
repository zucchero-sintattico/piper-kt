package piperkt.services.friendships.presentation

import io.micronaut.serde.annotation.Serdeable
import piperkt.services.friendships.domain.DirectMessage
import piperkt.services.friendships.domain.DirectMessageId

@Serdeable
data class DirectMessageDTO(
    val id: String,
    val sender: String,
    val content: String,
    val timestamp: String = java.time.Instant.now().toString(),
) {
    fun toDomain(): DirectMessage {
        return DirectMessage(
            id = DirectMessageId(id),
            sender = sender,
            content = content,
            timestamp = java.time.Instant.parse(timestamp)
        )
    }

    companion object {
        fun fromDomain(directMessage: DirectMessage): DirectMessageDTO {
            return DirectMessageDTO(
                id = directMessage.id.value,
                sender = directMessage.sender,
                content = directMessage.content,
                timestamp = directMessage.timestamp.toString()
            )
        }
    }
}
