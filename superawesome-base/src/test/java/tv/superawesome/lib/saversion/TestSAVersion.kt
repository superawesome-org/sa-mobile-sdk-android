package tv.superawesome.lib.saversion

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import tv.superawesome.sdk.publisher.SAVersion

class TestSAVersion {

    private val versionOverride = "5.6.7"
    private val sdkOverride = "unity"
    private val pluginName = "admob"

    @Before
    fun setUp() {
        // Reset default SDK value
        SAVersion.overrideSdk("android")
    }

    @Test
    fun test_Version_Override_With_PluginName() {

        // given
        val expectedSDKVersion = "android_5.6.7_admob"

        // when
        SAVersion.overrideVersion(versionOverride)

        // then
        Assert.assertEquals(expectedSDKVersion, SAVersion.getSDKVersion(pluginName))
    }

    @Test
    fun test_Version_Override_Without_PluginName() {

        // given
        val expectedSDKVersion = "android_5.6.7"

        // when
        SAVersion.overrideVersion(versionOverride)

        // then
        Assert.assertEquals(expectedSDKVersion, SAVersion.getSDKVersion(null))
    }

    @Test
    fun test_SDK_Override_With_PluginName() {

        // given
        val expectedSDKVersion = "unity_5.6.7_admob"

        // when
        SAVersion.overrideVersion(versionOverride)
        SAVersion.overrideSdk(sdkOverride)

        // then
        Assert.assertEquals(expectedSDKVersion, SAVersion.getSDKVersion(pluginName))
    }

    @Test
    fun test_SAVersion_SDK_Override_Without_PluginName() {

        // given
        val expectedSDKVersion = "unity_5.6.7"

        // when
        SAVersion.overrideVersion(versionOverride)
        SAVersion.overrideSdk(sdkOverride)

        // then
        Assert.assertEquals(expectedSDKVersion, SAVersion.getSDKVersion(null))
    }

    @Test
    fun test_getSDKVersion_defaults() {

        // given
        val expectedSDK = "android"

        // when
        val sdkVersionComponents = SAVersion.getSDKVersion(null).split("_")
        val sdkName = sdkVersionComponents.getOrNull(0) ?: ""
        val version = sdkVersionComponents.getOrNull(1) ?: ""
        val strippedVersion = version.replace(".", "")
        val isVersionNumeric = strippedVersion.all { char -> char.isDigit() }

        // then
        Assert.assertEquals(sdkName, expectedSDK)
        Assert.assertTrue(isVersionNumeric)
    }

    @Test
    fun test_getSDKVersionNumber_with_version_override() {

        // given
        val expectedSDK = "5.6.7"
        SAVersion.overrideVersion(versionOverride)
        val sdkVersionNumber = SAVersion.getSDKVersionNumber()

        // when
        val isVersionNumeric = sdkVersionNumber
            .replace(".", "")
            .all { char -> char.isDigit() }

        // then
        Assert.assertEquals(expectedSDK, sdkVersionNumber)
        Assert.assertTrue(isVersionNumeric)
    }

    @Test
    fun test_getSDKVersionNumber_defaults() {
        testVersionNumber(SAVersion.getSDKVersionNumber(), true)
        testVersionNumber("1.2.3", true)
        testVersionNumber("a.b.c", false)
    }

    private fun testVersionNumber(versionNumber: String, isNumeric: Boolean) {
        val firstPart = versionNumber
            .replace(".", "")
            .split("-")
            .first()


        // when
        val isVersionNumeric = firstPart
            .replace(".", "")
            .all { char -> char.isDigit() }

        // then
        Assert.assertEquals(isVersionNumeric, isNumeric)
    }
}