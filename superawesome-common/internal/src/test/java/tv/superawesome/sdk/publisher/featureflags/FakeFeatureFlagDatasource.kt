package tv.superawesome.sdk.publisher.featureflags

import java.io.IOException

class FakeFeatureFlagDatasource : FeatureFlagsDatasource {

    override suspend fun getFlags(): FeatureFlags = FeatureFlags(isAdResponseVASTEnabled = true)
}

class FailingFeatureFlagDatasource : FeatureFlagsDatasource {
    override suspend fun getFlags(): FeatureFlags {
        throw IOException()
    }
}
