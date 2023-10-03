package tv.superawesome.sdk.publisher.ad

sealed class AdExceptions : Exception() {

    data object MissingVastTagError : AdExceptions()
}
