package piperkt.services.multimedia.application

fun <T> T.asSuccess(): Result<T> {
    return Result.success(this)
}

fun <E : Exception> E.asFailure(): Result<Nothing> {
    return Result.failure(this)
}
