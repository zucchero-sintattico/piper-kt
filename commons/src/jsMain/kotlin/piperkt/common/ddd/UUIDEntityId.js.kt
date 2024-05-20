package piperkt.common.ddd

import kotlin.random.Random

actual fun getUUID(): String {
    return Random.Default.nextLong().toString()
}