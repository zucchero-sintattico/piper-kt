package piperkt.common.servers

import java.util.*
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
