package tv.superawesome.lib.saversion

import android.content.Context
import io.mockk.every
import io.mockk.mockk
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
    fun test_SAVersion_Version_Override_With_PluginName() {

        val context = mockk<Context>(relaxed = true)

        // given
        val expectedSDKVersion = "android_5.6.7_admob"

        // when
        SAVersion.overrideVersion(versionOverride)

        // then
        Assert.assertEquals(expectedSDKVersion, SAVersion.getSDKVersion(context, pluginName))
    }

    @Test
    fun test_SAVersion_Version_Override_Without_PluginName() {

        val context = mockk<Context>(relaxed = true)

        // given
        val expectedSDKVersion = "android_5.6.7"

        // when
        SAVersion.overrideVersion(versionOverride)

        // then
        Assert.assertEquals(expectedSDKVersion, SAVersion.getSDKVersion(context, null))
    }

    @Test
    fun test_SAVersion_SDK_Override_With_PluginName() {

        val context = mockk<Context>(relaxed = true)

        // Reads from version.properties as a version override is not set
        every { context.assets.open("version.properties") } returns "version.name=5.6.7".byteInputStream()

        // given
        val expectedSDKVersion = "unity_5.6.7_admob"

        // when
        SAVersion.overrideSdk(sdkOverride)

        // then
        Assert.assertEquals(expectedSDKVersion, SAVersion.getSDKVersion(context, pluginName))
    }

    @Test
    fun test_SAVersion_SDK_Override_Without_PluginName() {

        val context = mockk<Context>(relaxed = true)

        // Reads from version.properties as a version override is not set
        every { context.assets.open("version.properties") } returns "version.name=5.6.7".byteInputStream()

        // given
        val expectedSDKVersion = "unity_5.6.7"

        // when
        SAVersion.overrideSdk(sdkOverride)

        // then
        Assert.assertEquals(expectedSDKVersion, SAVersion.getSDKVersion(context, null))
    }
}