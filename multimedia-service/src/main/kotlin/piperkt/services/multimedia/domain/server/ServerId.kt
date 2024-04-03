package piperkt.services.multimedia.domain.server

import piperkt.services.multimedia.common.EntityId

class ServerId(value: String) : EntityId<String>(value) {
    companion object {
        fun empty() = ServerId("")
    }
}
