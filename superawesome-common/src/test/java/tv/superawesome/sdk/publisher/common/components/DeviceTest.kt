package tv.superawesome.sdk.publisher.common.components

import android.util.DisplayMetrics
import io.mockk.mockk
import org.junit.Test
import tv.superawesome.sdk.publisher.common.base.BaseTest
import kotlin.test.assertEquals

class DeviceTest : BaseTest() {
    @Test
    fun testDeviceCategory_phone() {
        // Given
        val galaxyS9Metrics: DisplayMetrics = mockk {
            widthPixels = 1440
            heightPixels = 2960
            densityDpi = 570
        }
        val device = Device(galaxyS9Metrics)

        // When
        val deviceCategory = device.genericType

        // Then
        assertEquals(DeviceCategory.phone, deviceCategory)
    }

    @Test
    fun testDeviceCategory_tablet() {
        // Given
        val galaxyTabMetrics: DisplayMetrics = mockk {
            widthPixels = 2560
            heightPixels = 1600
            densityDpi = 360
        }
        val device = Device(galaxyTabMetrics)

        // When
        val deviceCategory = device.genericType

        // Then
        assertEquals(DeviceCategory.tablet, deviceCategory)
    }
}