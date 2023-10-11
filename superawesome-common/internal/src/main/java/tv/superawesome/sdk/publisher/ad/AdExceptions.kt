package tv.superawesome.sdk.publisher.ad

/**
 * Exceptions that can be thrown when loading ads.
 */
sealed class AdExceptions : Exception() {

    /**
     * Ad is missing VAST tag.
     */
    data object MissingVastTagError : AdExceptions()
}
