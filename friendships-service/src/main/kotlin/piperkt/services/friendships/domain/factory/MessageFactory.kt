package piperkt.services.friendships.domain.factory

import java.time.Instant
import piperkt.common.Factory
import piperkt.common.id.MessageId
import piperkt.services.friendships.domain.Message

object MessageFactory : Factory<Message> {

    fun createMessage(
        content: String,
        sender: String,
        timeStamp: String = Instant.now().toString(),
        id: String = MessageId().value
    ) =
        Message(
            sender = sender,
            content = content,
            timestamp = Instant.parse(timeStamp),
            id = MessageId(id)
        )
}
