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
  private final Executor executor;
  private ISASession session;

  private final SAPerformanceTimer closeButtonPressedTimer = new SAPerformanceTimer();
  private final SAPerformanceTimer dwellTimeTimer = new SAPerformanceTimer();
  private final SAPerformanceTimer loadTimeTimer = new SAPerformanceTimer();
  private final SAPerformanceTimer renderTimeTimer = new SAPerformanceTimer();

  public SAPerformanceMetrics() {
    this(Executors.newSingleThreadExecutor());
  }

  public SAPerformanceMetrics(Executor executor) {
    this.executor = executor;
  }

  public void setSession(ISASession session) {
    this.session = session;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////////
  // Time Tracking
  ////////////////////////////////////////////////////////////////////////////////////////////////
  public void startTimingForLoadTime() {
    loadTimeTimer.start(new Date().getTime());
  }

  public void trackLoadTime() {
    if (loadTimeTimer.getStartTime() == 0L) { return; }

    SAPerformanceMetricModel model = new SAPerformanceMetricModel(
        loadTimeTimer.delta(new Date().getTime()),
        SAPerformanceMetricName.LoadTime,
        SAPerformanceMetricType.Gauge
    );

    sendPerformanceMetric(model, session);
  }
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

  public void startTimerForRenderTime() {
    renderTimeTimer.start(new Date().getTime());
  }

  public void trackRenderTime() {
    if (renderTimeTimer.getStartTime() == 0L) { return; }

    SAPerformanceMetricModel model = new SAPerformanceMetricModel(
      renderTimeTimer.delta(new Date().getTime()),
      SAPerformanceMetricName.RenderTime,
      SAPerformanceMetricType.Gauge
    );

    sendPerformanceMetric(model, session);
  }

  ////////////////////////////////////////////////////////////////////////////////////////////////
  // Conveniences
  ////////////////////////////////////////////////////////////////////////////////////////////////

  private void sendPerformanceMetric(SAPerformanceMetricModel model,
                                     ISASession session) {
    if (session == null) { return; }

    final SAPerformanceMetricDispatcher metric = new SAPerformanceMetricDispatcher(
        model, session, executor, 15000, false
    );

    metric.sendMetric();
  }
}
