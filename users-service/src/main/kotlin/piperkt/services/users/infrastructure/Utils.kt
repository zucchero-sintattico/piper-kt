package piperkt.services.users.infrastructure

import java.util.*

object Utils {
    fun <T> Optional<T>.asNullable(): T = orElse(null)
}
