package tv.superawesome.lib.featureflags

import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test
import tv.superawesome.lib.saevents.mocks.session.MockSession

class FeatureFlagsManagerTests {

    @Test
    fun `feature flag manager correctly loads flags`() {
        // given
        val featureFlags = FeatureFlags(isAdResponseVASTEnabled = true)
        val ffApi = mockk<GlobalFeatureFlagsApi>(relaxed = true)
        val sut = FeatureFlagsManager(ffApi)

        every { ffApi.getGlobalFlags(sut) } answers {
            sut.didLoadFeatureFlags(featureFlags = featureFlags)
        }

        // when
        sut.fetchFeatureFlags()

        // then
        assertTrue(sut.featureFlags.isAdResponseVASTEnabled)
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
        assertFalse(sut.featureFlags.isAdResponseVASTEnabled)
        assertFalse(sut.featureFlags.isAdResponseVASTEnabled)
        assertEquals(2500, sut.featureFlags.videoStabilityFailsafeTimeout)
    }
}
