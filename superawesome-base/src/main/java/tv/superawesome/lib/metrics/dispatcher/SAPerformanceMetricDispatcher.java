package tv.superawesome.lib.metrics.dispatcher;

import android.util.Log;

import org.json.JSONObject;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import tv.superawesome.lib.metrics.models.SAPerformanceMetricModel;
import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.sanetwork.request.SANetwork;
import tv.superawesome.lib.sasession.session.ISASession;
import tv.superawesome.lib.sautils.SAUtils;

public class SAPerformanceMetricDispatcher {
  protected final SAPerformanceMetricModel metric;
  protected final ISASession session;
  private final SANetwork network;
  private final boolean isDebug;

  public SAPerformanceMetricDispatcher(SAPerformanceMetricModel metric,
                                       ISASession session) {
    this(metric, session, Executors.newSingleThreadExecutor(), 15000, false);
  }

  public SAPerformanceMetricDispatcher(SAPerformanceMetricModel metric,
                                       ISASession session,
                                       Executor executor,
                                       int timeout,
                                       boolean isDebug) {
    this.metric = metric;
    this.session = session;
    this.isDebug = isDebug;
    this.network = new SANetwork(executor, timeout);
  }

  public String getUrl() {
    try {
      return session.getBaseUrl();
    } catch (Exception e) {
      return null;
    }
  }

  public String getEndpoint() {
    return "/sdk/performance";
  }

  public JSONObject getQuery() {
    try {
      return SAJsonParser.newObject(
          "value", metric.value,
          "metricName", metric.metricName.label,
          "metricType", metric.metricType.label
      );
    } catch (Exception e) {
      return new JSONObject();
    }
  }

  public JSONObject getHeader() {
    if (session != null) {
      return SAJsonParser.newObject(
          "Content-Type", "application/json", "User-Agent", session.getUserAgent());
    } else {
      return SAJsonParser.newObject("Content-Type", "application/json");
    }
  }

  public void sendMetric() {
    JSONObject query = getQuery();
    network.sendGET(
        getUrl() + getEndpoint(),
        query,
        getHeader(),
        (status, payload, success) -> {
          if (!isDebug) {
            String url = getUrl() + getEndpoint() + "?" + SAUtils.formGetQueryFromDict(query);
            Log.d("SuperAwesome", success + " | " + status + " | " + url);
          }
        });
  }
}

