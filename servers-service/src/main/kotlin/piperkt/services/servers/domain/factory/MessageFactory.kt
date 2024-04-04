package piperkt.services.servers.domain.factory

import java.time.Instant
import piperkt.common.id.MessageId
import piperkt.services.servers.domain.Message

object MessageFactory {

    fun createMessage(
        messageId: String,
        content: String,
        sender: String,
        timeStamp: String? = Instant.now().toString()
    ) =
        Message(
            messageId = MessageId(messageId),
            sender = sender,
            content = content,
            timestamp = Instant.parse(timeStamp)
        )
}
