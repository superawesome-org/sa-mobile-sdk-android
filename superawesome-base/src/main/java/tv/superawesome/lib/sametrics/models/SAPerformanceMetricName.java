package tv.superawesome.lib.sametrics.models;

public enum SAPerformanceMetricName {
  CloseButtonPressTime("sa.ad.sdk.close.button.press.time"),

  DwellTime("sa.ad.sdk.dwell.time"),

  LoadTime("sa.ad.sdk.performance.load.time"),

  RenderTime("sa.ad.sdk.performance.render.time");

  public final String label;

  SAPerformanceMetricName(String label) {
    this.label = label;
  }
}
