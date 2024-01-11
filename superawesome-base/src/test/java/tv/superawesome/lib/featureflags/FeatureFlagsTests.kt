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
        val jsonObject = JSONObject()
        jsonObject.put("isAdResponseVASTEnabled", true)
        jsonObject.put("isExoPlayerEnabled", true)
        jsonObject.put("videoStabilityFailsafeTimeout", 5)

        // when
        val featureFlags = FeatureFlags.getFlagsFromJSON(jsonObject)

        // then
        assertTrue(featureFlags.isAdResponseVASTEnabled)
        assertTrue(featureFlags.isExoPlayerEnabled)
        assertEquals(5, featureFlags.videoStabilityFailsafeTimeout)
    }

    @Test
    fun `feature flags can be initialised with correct json object but incorrect type`() {
        // given
        val jsonObject = JSONObject()
        jsonObject.put("isAdResponseVASTEnabled", true)
        jsonObject.put("isExoPlayerEnabled", true)
        jsonObject.put("videoStabilityFailsafeTimeout", false)

        // when
        val featureFlags = FeatureFlags.getFlagsFromJSON(jsonObject)

        // then
        assertTrue(featureFlags.isAdResponseVASTEnabled)
        assertTrue(featureFlags.isExoPlayerEnabled)
        assertEquals(2500, featureFlags.videoStabilityFailsafeTimeout)
    }

    @Test
    fun `feature flags can be initialised with partially correct json object`() {
        // given
        val jsonObject = JSONObject()
        jsonObject.put("isAdResponseVASTEnabled", true)
        jsonObject.put("isExoPlayerEnabled", true)

        // when
        val featureFlags = FeatureFlags.getFlagsFromJSON(jsonObject)

        // then
        assertTrue(featureFlags.isAdResponseVASTEnabled)
        assertTrue(featureFlags.isExoPlayerEnabled)
        assertEquals(2500, featureFlags.videoStabilityFailsafeTimeout)
    }

    @Test
    fun `feature flags can not be initialised with incorrect json object and return default`() {
        // given
        val jsonObject = JSONObject()

        // when
        val featureFlags = FeatureFlags.getFlagsFromJSON(jsonObject)

        // then
        assertFalse(featureFlags.isAdResponseVASTEnabled)
        assertFalse(featureFlags.isExoPlayerEnabled)
        assertEquals(2500, featureFlags.videoStabilityFailsafeTimeout)
    }
}
