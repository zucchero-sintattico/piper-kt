package piperkt.services.friendships.domain.factory

import java.time.Instant
import piperkt.common.ddd.Factory
import piperkt.services.friendships.domain.DirectMessage
import piperkt.services.friendships.domain.DirectMessageId

object MessageFactory : Factory<DirectMessage> {

    fun createMessage(
        content: String,
        sender: String,
        timeStamp: String = Instant.now().toString(),
        id: String = DirectMessageId().value,
    ) =
        DirectMessage(
            sender = sender,
            content = content,
            timestamp = Instant.parse(timeStamp),
            id = DirectMessageId(id)
        )
}
