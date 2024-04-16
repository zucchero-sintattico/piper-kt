package piperkt.services.multimedia.domain.server

import piperkt.common.AggregateRoot
import piperkt.services.multimedia.domain.user.Username

class Server(
    id: ServerId = ServerId.empty(),
    private var members: Set<Username> = emptySet(),
    private var channels: Set<Channel> = emptySet()
) : AggregateRoot<ServerId>(id) {

    fun members(): Set<Username> {
        return members
    }

    fun channels(): Set<Channel> {
        return channels
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
            throw ServerErrors.ChannelNotInServer(id, channel.id)
        }
        channels -= channel
    }

    fun removeChannelById(channelId: ChannelId) {
        if (channels.none { it.id == channelId }) {
            throw ServerErrors.ChannelNotInServer(id, channelId)
        }
        channels = channels.filter { it.id != channelId }.toSet()
    }

    fun getChannelById(channelId: ChannelId): Channel {
        return channels.first { it.id == channelId }
    }
}
