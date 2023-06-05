package tv.superawesome.lib.sametrics;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import tv.superawesome.lib.sametrics.dispatcher.SAPerformanceMetricDispatcher;
import tv.superawesome.lib.sametrics.models.SAPerformanceMetricModel;
import tv.superawesome.lib.sametrics.models.SAPerformanceMetricName;
import tv.superawesome.lib.sametrics.models.SAPerformanceMetricType;
import tv.superawesome.lib.sasession.session.ISASession;

public class SAPerformanceMetrics {
  private Executor executor;
  private ISASession session;

  private long closeButtonPressedMetricStartTime = 0L;

  public SAPerformanceMetrics(ISASession session) {
    this(session, Executors.newSingleThreadExecutor());
  }

  public SAPerformanceMetrics(ISASession session,
                              Executor executor) {
    this.session = session;
    this.executor = executor;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////////
  // Time Tracking
  ////////////////////////////////////////////////////////////////////////////////////////////////

  public void startTimingForCloseButtonPressed() {
    closeButtonPressedMetricStartTime = new Date().getTime();
  }

  public void trackCloseButtonPressed() {
    if(closeButtonPressedMetricStartTime == 0L) { return; }

    long delta = new Date().getTime() - closeButtonPressedMetricStartTime;

    SAPerformanceMetricModel model = new SAPerformanceMetricModel(
        delta,
        SAPerformanceMetricName.CloseButtonPressTime,
        SAPerformanceMetricType.Gauge
    );

    sendPerformanceMetric(model, session);
  }

  ////////////////////////////////////////////////////////////////////////////////////////////////
  // Conveniences
  ////////////////////////////////////////////////////////////////////////////////////////////////

  private void sendPerformanceMetric(SAPerformanceMetricModel model,
                                    ISASession session) {

    final SAPerformanceMetricDispatcher metric = new SAPerformanceMetricDispatcher(
        model, session, executor, 15000, false
    );

    metric.sendMetric();
  }
}
