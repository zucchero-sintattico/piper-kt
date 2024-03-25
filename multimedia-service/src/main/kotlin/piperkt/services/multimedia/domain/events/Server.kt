package piperkt.services.multimedia.domain.events

data class ServerCreated(val serverId: String, val owner: String)

data class ServerDeleted(val serverId: String)
