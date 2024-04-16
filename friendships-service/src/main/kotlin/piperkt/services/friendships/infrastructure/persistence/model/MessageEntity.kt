package piperkt.services.friendships.infrastructure.persistence.model

import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import java.time.Instant
import piperkt.services.friendships.domain.Message
import piperkt.services.friendships.domain.factory.MessageFactory

@MappedEntity
data class MessageEntity(
    @Id val id: String,
    val content: String,
    val sender: String,
    val timestamp: String = Instant.now().toString()
) {
    companion object {
        fun fromDomain(message: Message) =
            MessageEntity(
                id = message.id.value,
                content = message.content,
                sender = message.sender,
                timestamp = message.timestamp.toString()
            )
    }
}

fun MessageEntity.toDomain() =
    MessageFactory.createMessage(id = id, content = content, sender = sender, timeStamp = timestamp)
