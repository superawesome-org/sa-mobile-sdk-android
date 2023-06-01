package tv.superawesome.lib.metrics.models;

enum SAPerformanceMetricType {
  Gauge( "gauge"),

  Increment("increment"),

  DecrementBy("decrementBy"),

  Decrement("decrement"),

  Histogram("histogram"),

  IncrementBy("incrementBy"),

  Timing("timing");

  public final String label;

  SAPerformanceMetricType(String label) {
    this.label = label;
  }
}
