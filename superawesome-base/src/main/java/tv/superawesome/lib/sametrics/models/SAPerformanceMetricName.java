package tv.superawesome.lib.sametrics.models;

public enum SAPerformanceMetricName {
  CloseButtonPressTime("sa.ad.sdk.close.button.press.time.android"),

  CloseButtonFallback("sa.ad.sdk.performance.fallback.close.shown.android"),

  FreezeCloseButtonFallback("sa.ad.sdk.performance.freeze.fallback.shown.android"),

  DwellTime("sa.ad.sdk.dwell.time.android"),

  LoadTime("sa.ad.sdk.performance.load.time.android"),

  RenderTime("sa.ad.sdk.performance.render.time.android");

  public final String label;

  SAPerformanceMetricName(String label) {
    this.label = label;
  }
}
