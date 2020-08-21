package tv.superawesome.sdk.publisher.common.network

class DataResult<T>(val value: Any?) {
    val isSuccess: Boolean = value !is Failure
    val isFailure: Boolean = value is Failure

    public fun getOrNull(): T? =
            when {
                isFailure -> null
                else -> value as T
            }

    companion object {
        fun <T> success(value: T): DataResult<T> = DataResult(value)
        fun <T> failure(exception: Throwable): DataResult<T> = DataResult(Failure(exception))
    }

    internal class Failure(val exception: Throwable)
}