package piperkt.services.multimedia.domain.server

import piperkt.common.EntityId

class ServerId(value: String) : EntityId<String>(value) {
    companion object {
        fun empty() = ServerId("")
    }
}
