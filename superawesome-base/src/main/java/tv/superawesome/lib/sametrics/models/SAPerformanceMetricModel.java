package tv.superawesome.lib.sametrics.models;

public class SAPerformanceMetricModel {
  public final Long value;
  public final SAPerformanceMetricName metricName;
  public final SAPerformanceMetricType metricType;

  public final SAPerformanceMetricTags metricTags;

  public SAPerformanceMetricModel(Long value,
                                  SAPerformanceMetricName metricName,
                                  SAPerformanceMetricType metricType,
                                  SAPerformanceMetricTags metricTags) {
    this.value = value;
    this.metricName = metricName;
    this.metricType = metricType;
    this.metricTags = metricTags;
  }
}
