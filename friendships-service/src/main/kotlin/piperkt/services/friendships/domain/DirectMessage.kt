package piperkt.services.friendships.domain

import java.time.Instant
import piperkt.common.ddd.Entity

class DirectMessage(
    override val id: DirectMessageId = DirectMessageId(),
    val sender: String,
    val content: String,
    val timestamp: Instant,
) : Entity<DirectMessageId>(id) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DirectMessage) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
