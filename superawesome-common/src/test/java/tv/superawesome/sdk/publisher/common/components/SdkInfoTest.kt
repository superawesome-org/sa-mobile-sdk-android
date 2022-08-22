package tv.superawesome.sdk.publisher.common.components

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import tv.superawesome.sdk.publisher.common.base.BaseTest
import java.util.*
import kotlin.test.assertEquals

class SdkInfoTest : BaseTest() {
    @Test
    fun testSdkInfo() {
        // Given
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

        val sdkInfo = SdkInfo(context, encoderType, locale)

        // Then
        assertEquals("testBundleName", sdkInfo.bundle)
        assertEquals("testAppName", sdkInfo.name)
        assertEquals("xx_YY", sdkInfo.lang)
        assertEquals("android_1.2.3", sdkInfo.version)
    }
}