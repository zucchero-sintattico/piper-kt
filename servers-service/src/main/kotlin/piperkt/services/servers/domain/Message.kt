package piperkt.services.servers.domain

import java.time.Instant
import piperkt.common.id.MessageId

class Message(
    val messageId: MessageId,
    val sender: String,
    val content: String,
    val timestamp: Instant,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Message) return false

        if (messageId != other.messageId) return false

        return true
    }

    override fun hashCode(): Int {
        return messageId.hashCode()
    }
}
