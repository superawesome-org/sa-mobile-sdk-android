package tv.superawesome.sdk.publisher.common.models

import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import org.junit.Assert
import org.junit.Test
import tv.superawesome.sdk.publisher.common.base.BaseTest
import tv.superawesome.sdk.publisher.common.components.TimeProviderType

internal class PerformanceTimerTest : BaseTest() {

    @MockK
    lateinit var timeProviderType: TimeProviderType

    @Test
    fun test_Delta_Calculation() {

        // Given
        val performanceTimer = PerformanceTimer()
        coEvery { timeProviderType.millis() } returns 10000L

        // When
        performanceTimer.start(timeProviderType.millis())
        coEvery { timeProviderType.millis() } returns 15000L

        // Then
        Assert.assertEquals(10000L, performanceTimer.startTime)
        Assert.assertEquals(5000L, performanceTimer.delta(timeProviderType.millis()))
    }

    @Test
    fun test_Delta_Calculation_Cannot_Be_Less_Than_Zero() {

        // Given
        val performanceTimer = PerformanceTimer()
        coEvery { timeProviderType.millis() } returns 10000L

        // When
        performanceTimer.start(timeProviderType.millis())
        coEvery { timeProviderType.millis() } returns 0L

        // Then
        Assert.assertEquals(10000L, performanceTimer.startTime)
        Assert.assertEquals(0L, performanceTimer.delta(timeProviderType.millis()))
    }
}