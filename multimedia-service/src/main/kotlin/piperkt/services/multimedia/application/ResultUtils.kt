package piperkt.services.multimedia.application

fun success() = Result.success(Unit)

fun failure(e: Exception) = Result.failure<Unit>(e)

fun <T> T.asSuccess(): Result<T> {
    return Result.success(this)
}

fun <E : Exception> E.asFailure(): Result<Nothing> {
    return Result.failure(this)
}

fun Any?.isNull() = this == null
