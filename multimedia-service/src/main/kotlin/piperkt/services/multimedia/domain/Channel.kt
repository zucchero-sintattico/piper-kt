package piperkt.services.multimedia.domain

data class ChannelId(val value: String)

class Channel(val id: ChannelId, val name: String, val session: Session) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Channel

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}

interface ChannelRepository {
    fun getSessionInChannel(channelId: ChannelId): Session?
}
