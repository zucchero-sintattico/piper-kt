package piperkt.services.servers.domain.factory

import piperkt.common.id.ServerId
import piperkt.services.servers.domain.Channel
import piperkt.services.servers.domain.Server

object ServerFactory {

    @Suppress("LongParameterList")
    fun createServer(
        serverId: String,
        name: String,
        description: String,
        owner: String,
        channels: List<Channel> = emptyList(),
        users: List<String> = listOf(owner)
    ): Server {
        return Server(
            id = ServerId(serverId),
            name = name,
            owner = owner,
            description = description,
            channels = channels.toMutableList(),
            users = users.toMutableList()
        )
    }

    fun createServerWithMembers(
        serverId: String,
        name: String,
        description: String,
        owner: String,
        members: List<String>
    ): Server {
        return Server(
            id = ServerId(serverId),
            name = name,
            owner = owner,
            description = description,
            users = members.toMutableList()
        )
    }

    fun createServerWithChannels(
        serverId: String,
        name: String,
        description: String,
        owner: String,
        channels: List<Channel>
    ): Server {
        return Server(
            id = ServerId(serverId),
            name = name,
            owner = owner,
            description = description,
            channels = channels.toMutableList()
        )
    }
}
