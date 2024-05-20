package piperkt.common.ddd

actual fun getUUID(): String {
    return java.util.UUID.randomUUID().toString()
}
