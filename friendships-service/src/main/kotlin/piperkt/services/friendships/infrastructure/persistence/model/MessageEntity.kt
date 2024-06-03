package piperkt.services.friendships.infrastructure.persistence.model

import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import java.time.Instant
import piperkt.services.friendships.domain.DirectMessage
import piperkt.services.friendships.domain.factory.MessageFactory

@MappedEntity
data class MessageEntity(
    @Id val id: String,
    val content: String,
    val sender: String,
    val timestamp: String = Instant.now().toString()
) {
    companion object {
        fun fromDomain(directMessage: DirectMessage) =
            MessageEntity(
                id = directMessage.id.value,
                content = directMessage.content,
                sender = directMessage.sender,
                timestamp = directMessage.timestamp.toString()
            )
    }
}

fun MessageEntity.toDomain() =
    MessageFactory.createMessage(id = id, content = content, sender = sender, timeStamp = timestamp)
