package tv.superawesome.lib.featureflags

import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class FeatureFlagsManagerTests {

    @Test
    fun `feature flag manager correctly loads flags`() {
        // given
        val featureFlags = FeatureFlags(isAdResponseVASTEnabled = FeatureFlag(
            value = true,
        ))
        val ffApi = mockk<GlobalFeatureFlagsApi>(relaxed = true)
        val sut = FeatureFlagsManager(ffApi)

        every { ffApi.getGlobalFlags(sut) } answers {
            sut.didLoadFeatureFlags(featureFlags = featureFlags)
        }

        // when
        sut.fetchFeatureFlags()

        // then
        assertTrue(sut.featureFlags.isAdResponseVASTEnabled.value)
    }

    @Test
    fun `feature flag manager loads default flags on failure`() {
        // given
        val exception = Exception("error")
        val ffApi = mockk<GlobalFeatureFlagsApi>(relaxed = true)
        val sut = FeatureFlagsManager(ffApi)

        every { ffApi.getGlobalFlags(sut) } answers {
            sut.didFailToLoadFeatureFlags(exception)
        }

        // when
        sut.fetchFeatureFlags()

        // then
        assertFalse(sut.featureFlags.isAdResponseVASTEnabled.value)
        assertFalse(sut.featureFlags.isAdResponseVASTEnabled.value)
        assertEquals(2_500L, sut.featureFlags.videoStabilityFailsafeTimeout.value)
        assertEquals(Long.MAX_VALUE, sut.featureFlags.rewardGivenAfterErrorDelay.value)
    }
}
