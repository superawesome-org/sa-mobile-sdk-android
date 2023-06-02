package tv.superawesome.lib.metrics.models;

public class SAPerformanceMetricModel {
  public Double value;
  public SAPerformanceMetricName metricName;
  public SAPerformanceMetricType metricType;

  public SAPerformanceMetricModel(double value,
                                  SAPerformanceMetricName metricName,
                                  SAPerformanceMetricType metricType) {
    this.value = value;
    this.metricName = metricName;
    this.metricType = metricType;
  }
}
