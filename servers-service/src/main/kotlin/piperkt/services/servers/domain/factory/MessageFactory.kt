package piperkt.services.servers.domain.factory

import java.time.Instant
import piperkt.common.ddd.Factory
import piperkt.services.servers.domain.ChannelMessage
import piperkt.services.servers.domain.ChannelMessageId

object MessageFactory : Factory<ChannelMessage> {

    fun createMessage(
        content: String,
        sender: String,
        timeStamp: String = Instant.now().toString(),
        id: String = ChannelMessageId().value,
    ) =
        ChannelMessage(
            sender = sender,
            content = content,
            timestamp = Instant.parse(timeStamp),
            id = ChannelMessageId(id)
        )
}
