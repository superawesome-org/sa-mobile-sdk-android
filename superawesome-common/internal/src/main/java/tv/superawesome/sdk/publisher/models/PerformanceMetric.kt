package tv.superawesome.sdk.publisher.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

data class PerformanceMetric(
    val value: Long,
    val metricName: PerformanceMetricName,
    val metricType: PerformanceMetricType,
    val metricTags: PerformanceMetricTags,
) {
  fun build(json: Json): Map<String, Any> {
    return mapOf(
        "value" to value,
        "metricName" to metricName.value,
        "metricType" to metricType.value,
        "metricTags" to json.encodeToString(PerformanceMetricTags.serializer(), metricTags),
    )}
}

enum class PerformanceMetricName(val value: String) {
  CloseButtonPressTime("sa.ad.sdk.close.button.press.time.android"),

  DwellTime("sa.ad.sdk.dwell.time.android"),

  LoadTime("sa.ad.sdk.performance.load.time.android"),

  RenderTime("sa.ad.sdk.performance.render.time.android")
}

enum class PerformanceMetricType(val value: String) {
  Gauge( "gauge"),

  Increment("increment"),

  DecrementBy("decrementBy"),

  Decrement("decrement"),

  Histogram("histogram"),

  IncrementBy("incrementBy"),

  Timing("timing")
}

@Serializable
data class PerformanceMetricTags(
    val placementId: Int,
    val lineItemId: Int,
    val creativeId: Int,
    val format: CreativeFormatType,
    val sdkVersion: String,
    val connectionType: Int,
)
