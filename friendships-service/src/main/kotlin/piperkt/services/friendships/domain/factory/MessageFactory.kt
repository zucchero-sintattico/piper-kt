package piperkt.services.friendships.domain.factory

import java.time.Instant
import piperkt.common.ddd.Factory
import piperkt.services.friendships.domain.Message
import piperkt.services.friendships.domain.MessageId

object MessageFactory : Factory<Message> {

    fun createMessage(
        content: String,
        sender: String,
        timeStamp: String = Instant.now().toString(),
        id: String = MessageId().value,
    ) =
        Message(
            sender = sender,
            content = content,
            timestamp = Instant.parse(timeStamp),
            id = MessageId(id)
        )
}
