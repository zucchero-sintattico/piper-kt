package piperkt.services.multimedia.domain.server

import piperkt.services.multimedia.domain.user.Username

sealed class ServerErrors : Exception() {
    data class ServerNotFound(val id: ServerId) : ServerErrors()

    data class UserAlreadyInServer(val id: ServerId, val username: Username) : ServerErrors()

    data class UserNotOwner(val id: ServerId, val username: Username) : ServerErrors()

    data class UserNotInServer(val id: ServerId, val username: Username) : ServerErrors()

    data class ChannelAlreadyInServer(val id: ServerId, val channel: Channel) : ServerErrors()

    data class ChannelNotInServer(val id: ServerId, val channelId: ChannelId) : ServerErrors()
}
