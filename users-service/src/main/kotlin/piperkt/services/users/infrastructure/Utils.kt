package piperkt.services.multimedia.infrastructure

import java.util.*

object Utils {
    fun <T> Optional<T>.asNullable(): T = orElse(null)
}
