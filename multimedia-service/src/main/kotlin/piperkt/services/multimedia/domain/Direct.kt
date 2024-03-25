package piperkt.services.multimedia.domain

data class DirectId(val value: Set<String>)

class Direct(val id: DirectId, val sessionId: SessionId) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Direct

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}

interface DirectRepository {
    fun getDirectById(directId: DirectId): Direct?

    fun getSessionInDirect(directId: DirectId): Session?
}
