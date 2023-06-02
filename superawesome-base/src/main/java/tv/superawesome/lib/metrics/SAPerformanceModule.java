package tv.superawesome.lib.metrics;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import tv.superawesome.lib.metrics.models.SAPerformanceMetricModel;
import tv.superawesome.lib.metrics.models.SAPerformanceMetricName;
import tv.superawesome.lib.metrics.models.SAPerformanceMetricType;
import tv.superawesome.lib.sasession.session.ISASession;

public class SAPerformanceModule {
  private final Executor executor;

  public SAPerformanceModule() {
    this(Executors.newSingleThreadExecutor());
  }

  public SAPerformanceModule(Executor executor) {
    this.executor = executor;
  }

  public void sendPerformanceMetric(SAPerformanceMetricModel model,
                                    ISASession session,
                                    SAPerformanceMetric.Listener listener) {

    final SAPerformanceMetric metric = new SAPerformanceMetric(
        model, session, executor, 15000, false
    );

    if (metric != null) {
      metric.sendMetric(listener);
    }
  }
}
