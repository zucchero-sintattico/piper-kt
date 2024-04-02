package piperkt.services.multimedia.domain.server

import piperkt.services.multimedia.domain.AggregateRoot
import piperkt.services.multimedia.domain.user.UserId

class Server(
    id: ServerId = ServerId.empty(),
    private var members: List<UserId> = emptyList(),
    private var channels: List<Channel> = emptyList()
) : AggregateRoot<ServerId>(id) {

    fun members(): List<UserId> {
        return members.toList()
    }

    fun channels(): List<Channel> {
        return channels.toList()
    }

    @Throws(ServerErrors.UserAlreadyInServer::class)
    fun addMember(member: UserId) {
        if (members.contains(member)) {
            throw ServerErrors.UserAlreadyInServer(id, member)
        }
        members += member
    }

    @Throws(ServerErrors.UserNotInServer::class)
    fun removeMember(member: UserId) {
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
