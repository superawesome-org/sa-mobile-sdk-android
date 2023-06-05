package tv.superawesome.lib.sametrics.models;

public enum SAPerformanceMetricType {
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
