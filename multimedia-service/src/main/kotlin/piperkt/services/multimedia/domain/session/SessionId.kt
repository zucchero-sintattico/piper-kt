package piperkt.services.multimedia.domain.session

import piperkt.services.multimedia.domain.EntityId

class SessionId(value: String) : EntityId<String>(value) {
    companion object {
        fun empty() = SessionId("")
    }
}
