package piperkt.services.servers.domain.factory

import piperkt.services.commons.domain.id.ServerId
import piperkt.services.servers.domain.Server

object ServerFactory {

    fun createServer(
        serverId: String,
        name: String,
        description: String,
        owner: String,
    ): Server {
        return Server(
            id = ServerId(serverId),
            name = name,
            owner = owner,
            description = description
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
            members = members
        )
    }
}
