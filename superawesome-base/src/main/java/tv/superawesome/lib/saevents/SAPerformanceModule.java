package tv.superawesome.lib.saevents;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import tv.superawesome.lib.metrics.SAPerformanceMetric;
import tv.superawesome.lib.metrics.models.SAPerformanceMetricModel;
import tv.superawesome.lib.sasession.session.ISASession;

public class SAPerformanceModule {

  private final ISASession session;
  private final Executor executor;

  public SAPerformanceModule(ISASession session) {
    this(session, Executors.newSingleThreadExecutor());
  }

  public SAPerformanceModule(ISASession session,
                             Executor executor) {
    this.session = session;
    this.executor = executor;
  }

  public void triggerPerformanceMetric(SAPerformanceMetricModel model,
                                       SAPerformanceMetric.Listener listener,
                                       boolean isDebug) {

    final SAPerformanceMetric metric = new SAPerformanceMetric(
        model, session, executor, 15000, isDebug
    );

    if (metric != null) {
      metric.sendMetric(listener);
    }
  }
}
