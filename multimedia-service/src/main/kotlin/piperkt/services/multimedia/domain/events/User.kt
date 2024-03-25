package piperkt.services.multimedia.domain.events

data class UserJoinedChannel(val userId: String, val serverId: String, val channelId: String)

data class UserJoinedDirect(val userId: String, val directId: String)

data class UserJoinedServer(val username: String, val serverId: String)

data class UserKickedFromServer(val username: String, val serverId: String)

data class UserLeftChannel(val userId: String, val serverId: String, val channelId: String)

data class UserLeftDirect(val userId: String, val directId: String)

data class UserLeftServer(val username: String, val serverId: String)
