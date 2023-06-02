package tv.superawesome.lib.metrics;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import tv.superawesome.lib.metrics.dispatcher.SAPerformanceMetricDispatcher;
import tv.superawesome.lib.metrics.models.SAPerformanceMetricModel;
import tv.superawesome.lib.metrics.models.SAPerformanceMetricName;
import tv.superawesome.lib.metrics.models.SAPerformanceMetricType;
import tv.superawesome.lib.sasession.session.ISASession;

public class SAPerformanceMetrics {
  private final Executor executor;
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

  public void startTimingCloseButtonPressed() {
    closeButtonPressedMetricStartTime = new Date().getTime();
  }

  public void stopTimingCloseButtonPressed() {
    if(closeButtonPressedMetricStartTime == 0L) { return; }

    long delta = new Date().getTime() - closeButtonPressedMetricStartTime;

    SAPerformanceMetricModel model = new SAPerformanceMetricModel(
        delta,
        SAPerformanceMetricName.CloseButtonPressTime,
        SAPerformanceMetricType.Gauge
    );

    sendPerformanceMetric(model, session);
    closeButtonPressedMetricStartTime = 0L;
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