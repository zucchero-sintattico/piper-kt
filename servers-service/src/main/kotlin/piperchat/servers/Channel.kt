package piperchat.servers

import java.util.Date
import piperchat.commons.UserId

interface ChannelId {
    val value: String
}

interface Channel {
    val id: ChannelId
    val name: String
    val description: String
    val participants: List<UserId>
    val createdAt: Date
}
