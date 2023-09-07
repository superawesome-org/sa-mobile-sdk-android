package tv.superawesome.sdk.publisher.components

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import tv.superawesome.sdk.publisher.base.BaseTest
import tv.superawesome.sdk.publisher.models.Platform
import tv.superawesome.sdk.publisher.components.Encoder
import tv.superawesome.sdk.publisher.components.SdkInfo
import java.util.*
import kotlin.test.BeforeTest
import kotlin.test.assertEquals

internal class SdkInfoTest : BaseTest() {

    @BeforeTest
    fun prepare() {
        SdkInfo.Companion.overrideVersionPlatform(null, null)
    }

    @Test
    fun testSdkInfo() {
        // Given
        val sdkInfo = configureSdkInfo()

        // Then
        assertEquals("testBundleName", sdkInfo.bundle)
        assertEquals("testAppName", sdkInfo.name)
        assertEquals("xx_YY", sdkInfo.lang)
        assertEquals("android_1.2.3", sdkInfo.version)
        // The version number is read from the version.properties file in the test resources
        assertEquals("1.2.3", sdkInfo.versionNumber)
    }

    @Test
    fun testSdkInfo_with_version_override() {
        // Given
        val sdkInfo = configureSdkInfo()

        // When
        SdkInfo.Companion.overrideVersionPlatform("4.5.6", Platform.Android)

        // Then
        assertEquals("4.5.6", sdkInfo.versionNumber)
    }

    private fun configureSdkInfo(): SdkInfo {
        val locale = Locale("xx", "YY")
        val mockPackageManager = mockk<PackageManager> {
            every { getApplicationLabel(any()) } returns "testAppName"
        }
        val mockApplicationInfo = mockk<ApplicationInfo>()
        val context = mockk<Context> {
            every { packageName } returns "testBundleName"
            every { packageManager } returns mockPackageManager
            every { applicationInfo } returns mockApplicationInfo
        }
        val encoderType = Encoder()

        return SdkInfo(context, encoderType, locale)
    }
}