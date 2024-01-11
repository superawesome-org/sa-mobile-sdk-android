package tv.superawesome.lib.featureflags

/**
 * Interface for feature flags loader callbacks.
 */
interface SAFeatureFlagLoaderListener {

    /**
     * If the feature flags load was successful, this function is called with the feature flags.
     *
     * @param featureFlags the feature flags from the API.
     */
    fun didLoadFeatureFlags(featureFlags: FeatureFlags)

    /**
     * If the feature flags load was unsuccessful due to a network or json parsing error,
     * this function is called.
     *
     * @param error the throwable from the load feature flags api.
     */
    fun didFailToLoadFeatureFlags(error: Throwable)
}
