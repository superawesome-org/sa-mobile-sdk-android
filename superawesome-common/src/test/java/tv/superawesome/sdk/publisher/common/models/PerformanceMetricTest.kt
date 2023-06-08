package tv.superawesome.sdk.publisher.common.models

import org.junit.Test
import tv.superawesome.sdk.publisher.common.base.BaseTest
import kotlin.test.assertEquals

internal class PerformanceMetricTest : BaseTest() {

    @Test
    fun test_metricName() {
        val closeButtonPressTime = PerformanceMetricName.CloseButtonPressTime
        val dwellTime = PerformanceMetricName.DwellTime
        val loadTime = PerformanceMetricName.LoadTime
        val renderTime = PerformanceMetricName.RenderTime

        assertEquals("sa.ad.sdk.close.button.press.time", closeButtonPressTime.value)
        assertEquals("sa.ad.sdk.dwell.time", dwellTime.value)
        assertEquals("sa.ad.sdk.performance.load.time", loadTime.value)
        assertEquals("sa.ad.sdk.performance.render.time", renderTime.value)
    }

    @Test
    fun test_metricType() {
        val gauge = PerformanceMetricType.Gauge
        val increment = PerformanceMetricType.Increment
        val decrementBy = PerformanceMetricType.DecrementBy
        val decrement = PerformanceMetricType.Decrement
        val histogram = PerformanceMetricType.Histogram
        val incrementBy = PerformanceMetricType.IncrementBy
        val timing = PerformanceMetricType.Timing

        assertEquals("gauge", gauge.value)
        assertEquals("increment", increment.value)
        assertEquals("decrementBy", decrementBy.value)
        assertEquals("decrement", decrement.value)
        assertEquals("histogram", histogram.value)
        assertEquals("incrementBy", incrementBy.value)
        assertEquals("timing", timing.value)
    }

    @Test
    fun test_Build() {
        // Given
        val value = 10L
        val metricName = PerformanceMetricName.LoadTime
        val metricType = PerformanceMetricType.Gauge

        // When
        val metric = PerformanceMetric(value, metricName, metricType)
        val expectedMap = mapOf(
            "value" to 10L,
            "metricName" to "sa.ad.sdk.performance.load.time",
            "metricType" to "gauge"
        )

        // Then
        assertEquals(expectedMap, metric.build())
    }
}
