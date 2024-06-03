package piperkt.services.servers.domain

import java.time.Instant
import piperkt.common.ddd.Entity

class ChannelMessage(
    override val id: ChannelMessageId = ChannelMessageId(),
    val sender: String,
    val content: String,
    val timestamp: Instant,
) : Entity<ChannelMessageId>(id) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ChannelMessage) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
