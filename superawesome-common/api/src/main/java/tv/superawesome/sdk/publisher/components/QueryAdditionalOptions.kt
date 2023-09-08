package tv.superawesome.sdk.publisher.components

/**
 * Additional options when querying for ads.
 *
 * @property options dictionary of options. Options can be strings or ints.
 */
public data class QueryAdditionalOptions(val options: Map<String, Any>) {

    public companion object {
        /**
         * Get the latest instance of [QueryAdditionalOptions].
         */
        public var instance: QueryAdditionalOptions? = null
    }
}
