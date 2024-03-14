package piperkt.services.multimedia.application

fun <R, E> R.asSuccess(): Result<R, E> {
    return Result.success(this)
}

fun <R, E> E.asError(): Result<R, E> {
    return Result.error(this)
}

sealed class Result<R, E>(private val success: R? = null, private val error: E? = null) {

    data class Success<R, E>(val value: R) : Result<R, E>(success = value) {
        fun getSuccess(): R = value
    }

    data class Error<R, E>(val value: E) : Result<R, E>(error = value) {
        fun getError(): E = value
    }

    val isSuccess: Boolean
        get() = success != null

    val isFailure: Boolean
        get() = error != null

    companion object {
        fun <R, E> success(value: R): Result<R, E> {
            return Success(value)
        }

        fun <R, E> error(value: E): Result<R, E> {
            return Error(value)
        }
    }

    fun onSuccess(block: (R) -> Unit) {
        success?.let(block)
    }

    fun onError(block: (E) -> Unit) {
        error?.let(block)
    }
}
