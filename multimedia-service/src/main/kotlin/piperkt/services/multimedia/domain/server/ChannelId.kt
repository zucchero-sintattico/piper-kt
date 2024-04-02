package piperkt.services.multimedia.domain.server

import piperkt.services.multimedia.domain.EntityId

class ChannelId(value: Int) : EntityId<Int>(value) {
    companion object {
        fun empty() = ChannelId(0)
    }
}
