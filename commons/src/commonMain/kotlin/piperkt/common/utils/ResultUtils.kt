package piperkt.common.utils

fun success() = success(Unit)

fun <T> success(value: T) = Result.success(value)

fun failure(e: Exception) = Result.failure<Unit>(e)

fun <T> T.asSuccess(): Result<T> {
    return Result.success(this)
}

fun <E : Exception> E.asFailure(): Result<Nothing> {
    return Result.failure(this)
}

fun Any?.isNull() = this == null

fun <E> E?.orThrow(e: Exception): E {
    if (this == null) throw e
    return this
}
