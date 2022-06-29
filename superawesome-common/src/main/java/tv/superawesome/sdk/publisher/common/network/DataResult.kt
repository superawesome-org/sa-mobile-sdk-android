package tv.superawesome.sdk.publisher.common.network

sealed class DataResult<out T : Any> {
    data class Success<out T : Any>(val value: T) : DataResult<T>()
    data class Failure(val error: Throwable) : DataResult<Nothing>()

    val isSuccess: Boolean by lazy { this is Success }
    val isFailure: Boolean by lazy { this is Failure }

    val optValue: T?
        get() = (this as? Success)?.value
}