package tv.superawesome.sdk.publisher.featureflags

/**
 * Feature flags datasource implementation that gets FFs from the Ad server.
 */
class AdServerFeatureFlagsDataSource(
    private val adServerFeatureFlagsApi: AdServerFeatureFlagsApi,
) : FeatureFlagsDatasource {

    override suspend fun getFlags(query: FeatureFlagsQuery): FeatureFlags =
        adServerFeatureFlagsApi.getFlags(
            mapOf(
                "sdkVersion" to query.sdkVersion,
                "bundle" to query.bundle,
                "language" to query.language,
                "device" to query.device.name.lowercase()
            )
        )
}
