package tv.superawesome.lib.sametrics.models;

import org.junit.Assert;
import org.junit.Test;

public class SAPerformanceTimer_Test {

  @Test
  public void test_Delta_Calculation () {

    SAPerformanceTimer timer = new SAPerformanceTimer();

    timer.start(10000L);

    Long expectation = 5000L;
    Long delta = timer.delta(15000L);

    Assert.assertEquals(expectation, delta);
  }

  @Test
  public void test_Delta_Calculation_Cannot_Be_Less_Than_Zero () {

    SAPerformanceTimer timer = new SAPerformanceTimer();

    timer.start(10000L);

    Long expectation = 0L;
    Long delta = timer.delta(0L);

    Assert.assertEquals(expectation, delta);
  }
}
