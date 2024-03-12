package piperkt.services.servers.domain

import java.time.Instant
import piperkt.services.commons.domain.id.MessageId

class Message(
    val messageId: MessageId,
    val sender: String,
    val content: String,
    val timestamp: Instant
)
