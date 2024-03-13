package piperkt.services.multimedia.domain.users.events

data class UserJoinedChannel(val userId: String, val serverId: String, val channelId: String)
