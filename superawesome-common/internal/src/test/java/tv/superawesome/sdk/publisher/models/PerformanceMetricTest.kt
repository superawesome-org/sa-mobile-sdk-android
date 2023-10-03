package tv.superawesome.sdk.publisher.models

import kotlinx.serialization.json.Json
import org.junit.Test
import tv.superawesome.sdk.publisher.base.BaseTest
import kotlin.test.assertEquals

internal class PerformanceMetricTest : BaseTest() {

    val json = Json {
        allowStructuredMapKeys = true
        ignoreUnknownKeys = true
    }

    @Test
    fun test_metricName() {
        val closeButtonPressTime = PerformanceMetricName.CloseButtonPressTime
        val dwellTime = PerformanceMetricName.DwellTime
        val loadTime = PerformanceMetricName.LoadTime
        val renderTime = PerformanceMetricName.RenderTime

        assertEquals("sa.ad.sdk.close.button.press.time.android", closeButtonPressTime.value)
        assertEquals("sa.ad.sdk.dwell.time.android", dwellTime.value)
        assertEquals("sa.ad.sdk.performance.load.time.android", loadTime.value)
        assertEquals("sa.ad.sdk.performance.render.time.android", renderTime.value)
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
        val metricTags = PerformanceMetricTags(
            placementId = "1",
            lineItemId = "2",
            creativeId = "3",
            format = CreativeFormatType.Tag,
            sdkVersion = "1.0",
            connectionType = "2",
        )

        // When
        val metric = PerformanceMetric(value, metricName, metricType, metricTags)
        val expectedMap = mapOf(
            "value" to 10L,
            "metricName" to "sa.ad.sdk.performance.load.time.android",
            "metricType" to "gauge",
            "metricTags" to "{\"placementId\":\"1\",\"lineItemId\":\"2\",\"creativeId\":\"3\",\"format\":\"tag\",\"sdkVersion\":\"1.0\",\"connectionType\":\"2\"}",
        )

        // Then
        assertEquals(expectedMap, metric.build(json))
    }
}
