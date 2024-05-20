package piperkt.common.ddd

open class UUIDEntityId(value: String = newId()) : EntityId<String>(value) {
    companion object {
        fun newId(): String = getUUID()
    }
}

expect fun getUUID(): String
