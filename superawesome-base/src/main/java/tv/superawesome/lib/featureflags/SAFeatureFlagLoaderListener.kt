package tv.superawesome.lib.featureflags

interface SAFeatureFlagLoaderListener {

    fun didLoadFeatureFlags(featureFlags: FeatureFlags)
    fun didFailToLoadFeatureFlags(error: Throwable)
}
