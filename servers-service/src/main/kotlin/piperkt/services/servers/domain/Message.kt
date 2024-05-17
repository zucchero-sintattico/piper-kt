package piperkt.services.servers.domain

import java.time.Instant
import piperkt.common.ddd.Entity
import piperkt.common.id.MessageId

class Message(
    override val id: MessageId = MessageId(),
    val sender: String,
    val content: String,
    val timestamp: Instant,
) : Entity<MessageId>(id) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Message) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
