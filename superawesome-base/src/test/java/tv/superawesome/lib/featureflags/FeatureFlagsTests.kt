package tv.superawesome.lib.featureflags

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.json.JSONObject
import org.junit.Test

class FeatureFlagsTests {

    @Test
    fun `feature flags can be initialised with correct json object`() {
        // given
        val jsonString = """{"isAdResponseVASTEnabled":{"value":true},"isExoPlayerEnabled":{"value":false},"videoStabilityFailsafeTimeout":{"value":2500},"rewardGivenAfterErrorDelay":{"value":2000}}"""
        val jsonObj = JSONObject(jsonString)
        // when
        val featureFlags = FeatureFlags.getFlagsFromJSON(jsonObj)

        // then
        assertTrue(featureFlags.isAdResponseVASTEnabled.value)
        assertFalse(featureFlags.isExoPlayerEnabled.value)
        assertEquals(2_500L, featureFlags.videoStabilityFailsafeTimeout.value)
        assertEquals(2_000L, featureFlags.rewardGivenAfterErrorDelay.value)
    }

    @Test
    fun `feature flags can be initialised with missing keys`() {
        // given
        val jsonString = """{"isAdResponseVASTEnabled":{"value":true},"isExoPlayerEnabled":{"value":false},"videoStabilityFailsafeTimeout":{"value":2500}}"""
        val jsonObj = JSONObject(jsonString)

        // when
        val featureFlags = FeatureFlags.getFlagsFromJSON(jsonObj)

        // then
        assertTrue(featureFlags.isAdResponseVASTEnabled.value)
        assertFalse(featureFlags.isExoPlayerEnabled.value)
        assertEquals(2_500L, featureFlags.videoStabilityFailsafeTimeout.value)
        // Default value
        assertEquals(Long.MAX_VALUE, featureFlags.rewardGivenAfterErrorDelay.value)
    }

    @Test
    fun `feature flags are initialized with default values if json is invalid`() {
        // given
        val jsonObj = JSONObject()

        // when
        val featureFlags = FeatureFlags.getFlagsFromJSON(jsonObj)

        // then
        assertFalse(featureFlags.isAdResponseVASTEnabled.value)
        assertFalse(featureFlags.isExoPlayerEnabled.value)
        assertEquals(2_500L, featureFlags.videoStabilityFailsafeTimeout.value)
        assertEquals(Long.MAX_VALUE, featureFlags.rewardGivenAfterErrorDelay.value)
    }
}
