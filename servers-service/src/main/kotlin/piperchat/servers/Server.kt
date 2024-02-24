package piperchat.servers

import java.util.Date
import piperchat.commons.UserId

interface ServerId {
    val value: String
}

interface Server {
    val id: ServerId
    val name: String
    val description: String
    val owner: UserId
    val participants: List<UserId>
    val createdAt: Date
    val channels: List<Channel>
}
