package tv.superawesome.demoapp.repository

/**
 * Result of fetching data.
 *
 * @param T type of result object.
 */
sealed class DataResult<out T : Any> {

    /**
     * Operation finished successfully.
     *
     * @param T type of data.
     * @property value the result object.
     */
    data class Success<out T : Any>(val value: T) : DataResult<T>()

    /**
     * An error occurred.
     *
     * @property error the thrown error.
     */
    data class Failure(val error: Throwable) : DataResult<Nothing>()

    /** Shorthand to know if this fetching was a success. */
    val isSuccess: Boolean by lazy { this is Success }

    /** Shorthand to know if the fetching failed. */
    val isFailure: Boolean by lazy { this is Failure }

    /**
     * Optional value, only returns a value when the operation was a success.
     */
    val optValue: T?
        get() = (this as? Success)?.value
}
