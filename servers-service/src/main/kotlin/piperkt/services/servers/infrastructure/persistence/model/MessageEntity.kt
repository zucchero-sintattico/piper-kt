package piperkt.services.servers.infrastructure.persistence.model

import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import java.time.Instant
import piperkt.services.servers.domain.ChannelMessage
import piperkt.services.servers.domain.factory.MessageFactory

@MappedEntity
data class MessageEntity(
    @Id val id: String,
    val content: String,
    val sender: String,
    val timestamp: String = Instant.now().toString()
) {
    fun toDomain() =
        MessageFactory.createMessage(
            id = id,
            content = content,
            sender = sender,
            timeStamp = timestamp
        )

    companion object {
        fun fromDomain(channelMessage: ChannelMessage) =
            MessageEntity(
                id = channelMessage.id.value,
                content = channelMessage.content,
                sender = channelMessage.sender,
                timestamp = channelMessage.timestamp.toString()
            )
    }
}
