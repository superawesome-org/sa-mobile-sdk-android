package tv.superawesome.lib.sametrics;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import tv.superawesome.lib.sametrics.dispatcher.SAPerformanceMetricDispatcher;
import tv.superawesome.lib.sametrics.models.SAPerformanceMetricModel;
import tv.superawesome.lib.sametrics.models.SAPerformanceMetricName;
import tv.superawesome.lib.sametrics.models.SAPerformanceMetricType;
import tv.superawesome.lib.sametrics.models.SAPerformanceTimer;
import tv.superawesome.lib.sasession.session.ISASession;

public class SAPerformanceMetrics {
  private Executor executor;
  private ISASession session;

  private SAPerformanceTimer closeButtonPressedTimer = new SAPerformanceTimer();
  private SAPerformanceTimer dwellTimeTimer = new SAPerformanceTimer();

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

  public void startTimingForDwellTime() {
    dwellTimeTimer.start(new Date().getTime());
  }

  public void trackDwellTime() {
    if(dwellTimeTimer.getStartTime() == 0L) { return; }

    SAPerformanceMetricModel model = new SAPerformanceMetricModel(
        dwellTimeTimer.delta(new Date().getTime()),
        SAPerformanceMetricName.DwellTime,
        SAPerformanceMetricType.Gauge
    );

    sendPerformanceMetric(model, session);
  }

  public void startTimingForCloseButtonPressed() {
    closeButtonPressedTimer.start(new Date().getTime());
  }

  public void trackCloseButtonPressed() {
    if(closeButtonPressedTimer.getStartTime() == 0L) { return; }

    SAPerformanceMetricModel model = new SAPerformanceMetricModel(
        closeButtonPressedTimer.delta(new Date().getTime()),
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
