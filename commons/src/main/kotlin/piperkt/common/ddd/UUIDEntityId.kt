package piperkt.common.ddd

import java.util.*

open class UUIDEntityId(value: String = newId()) : EntityId<String>(value) {
    companion object {
        fun newId(): String = UUID.randomUUID().toString()
    }
}
