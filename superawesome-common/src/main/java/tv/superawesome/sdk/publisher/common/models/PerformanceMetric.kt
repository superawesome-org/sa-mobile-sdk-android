package tv.superawesome.sdk.publisher.common.models

internal data class PerformanceMetric(val value: Long,
                                      val metricName: PerformanceMetricName,
                                      val metricType: PerformanceMetricType) {
  fun build(): Map<String, Any> {
    return mapOf(
        "value" to value,
        "metricName" to metricName.value,
        "metricType" to metricType.value
    )}
}

internal enum class PerformanceMetricName(val value: String) {
  CloseButtonPressTime("sa.ad.sdk.close.button.press.time.android"),

  DwellTime("sa.ad.sdk.dwell.time.android"),

  LoadTime("sa.ad.sdk.performance.load.time.android"),

  RenderTime("sa.ad.sdk.performance.render.time.android")
}

internal enum class PerformanceMetricType(val value: String) {
  Gauge( "gauge"),

  Increment("increment"),

  DecrementBy("decrementBy"),

  Decrement("decrement"),

  Histogram("histogram"),

  IncrementBy("incrementBy"),

  Timing("timing")
}
