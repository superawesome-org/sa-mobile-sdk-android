package tv.superawesome.lib.metrics.models;

public class SAPerformanceMetricModel {
  public final Double value;
  public final SAPerformanceMetricName metricName;
  public final SAPerformanceMetricType metricType;

  public SAPerformanceMetricModel(double value,
                                  SAPerformanceMetricName metricName,
                                  SAPerformanceMetricType metricType) {
    this.value = value;
    this.metricName = metricName;
    this.metricType = metricType;
  }
}
