package tv.superawesome.lib.sametrics.models;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

public class SAPerformanceMetricModel_Test {

  @Test
  public void test_metricName () {

    SAPerformanceMetricName closeButtonPressTime = SAPerformanceMetricName.CloseButtonPressTime;
    SAPerformanceMetricName dwellTime = SAPerformanceMetricName.DwellTime;
    SAPerformanceMetricName loadTime = SAPerformanceMetricName.LoadTime;
    SAPerformanceMetricName renderTime = SAPerformanceMetricName.RenderTime;

    assertEquals("sa.ad.sdk.close.button.press.time.android", closeButtonPressTime.label);
    assertEquals("sa.ad.sdk.dwell.time.android", dwellTime.label);
    assertEquals("sa.ad.sdk.performance.load.time.android", loadTime.label);
    assertEquals("sa.ad.sdk.performance.render.time.android", renderTime.label);
  }

  @Test
  public void test_metricType () {

    SAPerformanceMetricType gauge = SAPerformanceMetricType.Gauge;
    SAPerformanceMetricType increment = SAPerformanceMetricType.Increment;
    SAPerformanceMetricType decrementBy = SAPerformanceMetricType.DecrementBy;
    SAPerformanceMetricType decrement = SAPerformanceMetricType.Decrement;
    SAPerformanceMetricType histogram = SAPerformanceMetricType.Histogram;
    SAPerformanceMetricType incrementBy = SAPerformanceMetricType.IncrementBy;
    SAPerformanceMetricType timing = SAPerformanceMetricType.Timing;

    assertEquals("gauge", gauge.label);
    assertEquals("increment", increment.label);
    assertEquals("decrementBy", decrementBy.label);
    assertEquals("decrement", decrement.label);
    assertEquals("histogram", histogram.label);
    assertEquals("incrementBy", incrementBy.label);
    assertEquals("timing", timing.label);
  }
}

