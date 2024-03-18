package piperkt.services.multimedia.domain.sessions

data class SessionId(val value: String) {
    companion object {
        fun empty(): SessionId {
            return SessionId("")
        }
    }
}
