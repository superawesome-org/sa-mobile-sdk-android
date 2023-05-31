package tv.superawesome.sdk.publisher.common.models

internal data class PerformanceMetric(val value: Double,
                                      val metricName: PerformanceMetricName,
                                      val metricType: PerformanceMetricType) {
  fun build(): Map<String, Any> {
    return mapOf(
        "value" to value,
        "metricName" to metricName.value,
        "metricType" to metricType.value
    )}
}

enum class PerformanceMetricName(val value: String) {
  CloseButtonPressTime("sa.ad.sdk.close.button.press.time"),

  DwellTime("sa.ad.sdk.dwell.time"),

  LoadTime("sa.ad.sdk.performance.load.time"),

  RenderTime("sa.ad.sdk.performance.load.time")
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