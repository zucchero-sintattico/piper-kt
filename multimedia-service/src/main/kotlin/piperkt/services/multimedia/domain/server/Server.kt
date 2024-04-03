package piperkt.services.multimedia.domain.server

import piperkt.common.AggregateRoot
import piperkt.services.multimedia.domain.user.Username

class Server(
    id: ServerId = ServerId.empty(),
    private var members: List<Username> = emptyList(),
    private var channels: List<Channel> = emptyList()
) : AggregateRoot<ServerId>(id) {

    fun members(): List<Username> {
        return members.toList()
    }

    fun channels(): List<Channel> {
        return channels.toList()
    }

    @Throws(ServerErrors.UserAlreadyInServer::class)
    fun addMember(member: Username) {
        if (members.contains(member)) {
            throw ServerErrors.UserAlreadyInServer(id, member)
        }
        members += member
    }

    @Throws(ServerErrors.UserNotInServer::class)
    fun removeMember(member: Username) {
        if (!members.contains(member)) {
            throw ServerErrors.UserNotInServer(id, member)
        }
        members -= member
    }

    @Throws(ServerErrors.ChannelAlreadyInServer::class)
    fun addChannel(channel: Channel) {
        if (channels.contains(channel)) {
            throw ServerErrors.ChannelAlreadyInServer(id, channel)
        }
        channels += channel
    }

    @Throws(ServerErrors.ChannelNotInServer::class)
    fun removeChannel(channel: Channel) {
        if (!channels.contains(channel)) {
            throw ServerErrors.ChannelNotInServer(id, channel)
        }
        channels -= channel
    }

    companion object {
        fun findById(id: ServerId, serverRepository: ServerRepository): Server {
            return serverRepository.findById(id) ?: throw ServerErrors.ServerNotFound(id)
        }
    }
}
