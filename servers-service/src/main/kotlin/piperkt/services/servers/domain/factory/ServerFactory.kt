package piperkt.services.servers.domain.factory

import piperkt.common.Factory
import piperkt.common.id.ServerId
import piperkt.services.servers.domain.Channel
import piperkt.services.servers.domain.Server

object ServerFactory : Factory<Server> {

    @Suppress("LongParameterList")
    fun createServer(
        name: String,
        description: String,
        owner: String,
        channels: List<Channel> = emptyList(),
        users: List<String> = listOf(owner),
        id: String = ServerId().value
    ): Server {
        return Server(
            id = ServerId(id),
            name = name,
            owner = owner,
            description = description,
            channels = channels.toMutableList(),
            users = users.toMutableList()
        )
    }
}
